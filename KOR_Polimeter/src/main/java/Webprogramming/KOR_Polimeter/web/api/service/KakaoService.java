package Webprogramming.KOR_Polimeter.web.api.service;

//import Webprogramming.KOR_Polimeter.domain.Member;
import Webprogramming.KOR_Polimeter.web.api.model.User;
import Webprogramming.KOR_Polimeter.web.api.dto.KakaoDTO;
//import Webprogramming.KOR_Polimeter.repository.MemberRepository;
import Webprogramming.KOR_Polimeter.web.api.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class KakaoService {
    //@Autowired
    //private final MemberRepository memberRepository;
    //private final MemberService memberService;

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";

    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    private final static String KAKAO_LOGOUT_URL = "https://kapi.kakao.com/v1/user/logout";
    @Autowired
    private UserRepository userRepository;

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    } // https://kauth.kakao.com/pauth/authorize?client_id=KAKAO_CLIENT_ID&redirect_uri=KAKAO_REDIRECT_URL&response_type=code

    public static void logoutUser(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_LOGOUT_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        // 로그아웃 성공 여부 확인
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("로그아웃 성공");
        } else {
            System.out.println("로그아웃 실패: " + response.getStatusCode());
        }
    }

    public KakaoDTO getKakaoInfo(String code) throws Exception {
        if (code == null) {
            throw new Exception("Fail to get authorization code");
        }

        String accessToken;

        try {
            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            // 요청 파라미터 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", KAKAO_CLIENT_ID);
            params.add("client_secret", KAKAO_CLIENT_SECRET);
            params.add("code", code);
            params.add("redirect_uri", KAKAO_REDIRECT_URL);

            // RestTemplate을 사용한 POST 요청
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // 응답 상태 코드 확인
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new Exception("Failed to get access token: " + response.getStatusCode());
            }

            // JSON 파싱
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            // access_token 추출 및 검증
            accessToken = (String) jsonObj.get("access_token");
            if (accessToken == null || accessToken.isEmpty()) {
                throw new Exception("Access token is missing in the response");
            }

        } catch (HttpClientErrorException e) {
            // 카카오 API 호출 실패 시 응답 본문 로깅
            System.err.println("Error response from Kakao API: " + e.getResponseBodyAsString());
            throw new Exception("API call failed: " + e.getMessage(), e);
        } catch (ParseException e) {
            // JSON 파싱 실패 처리
            throw new Exception("Failed to parse JSON response: " + e.getMessage(), e);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new Exception("Unexpected error occurred: " + e.getMessage(), e);
        }

        // access_token을 사용해 사용자 정보 가져오기
        return getUserInfoWithToken(accessToken);
    }


    private KakaoDTO getUserInfoWithToken(String accessToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            RestTemplate rt = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = rt.exchange(
                    KAKAO_API_URI + "/v2/user/me",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            Long id = (Long) jsonObj.get("id");

            JSONObject kakaoAccount = (JSONObject) jsonObj.get("kakao_account");

            String email = null;
            //String nickname = null;
            String name = null;
            int gender_int = 0; String gender_str = null;
            String phone_number = null;
            String bday = null;
            String byear = null; // 이후 age로 변환됨. User 컬럼에 byear는 없음
            Long userid = null;
            String created_at = (String) jsonObj.get("connected_at");
            int age = 0;

            if (kakaoAccount != null) {
               // email = (String) kakaoAccount.get("email");
                name = (String) kakaoAccount.get("name");
                gender_str = (String) kakaoAccount.get("gender");
                //phone_number = (String) kakaoAccount.get("phone_number");
                //bday = (String) kakaoAccount.get("birthday");
                byear = (String) kakaoAccount.get("birthyear");
            }

            if (gender_str != null) { // gender String을 int로 바꿔줌
                if (gender_str.equals("female")) gender_int = 2;
                else if (gender_str.equals("male")) gender_int = 1;
                else gender_int = 3;
            }

            if (byear != null) {
                age = 1 + 2024 - Integer.parseInt(byear);
            }
            System.out.println("계정 id: " + id);
            System.out.println("name 이름: " + name);
            //System.out.println("birthday 생일: " + bday);
            //System.out.println("phone_number 전화번호: " + phone_number);
            //System.out.println("created_at 생성 날짜: " + created_at);
            System.out.println("gender 성별 (남자:1, 여자:2): " + gender_int);
            //System.out.println("email 이메일: " + email);
            System.out.println("나이 age: " + age);
            //System.out.println("byear: " + byear);

            // System.out.println("nickname 닉네임: " + nickname);

            /*Member member = new Member();
            member.setEmail(email);
            member.setUsername(nickname);
            MemberService k = new MemberService(memberRepository);
            k.saveMember(member);
            */

            User user = new User();
            user.setUserId(id);
            user.setName(name);
            //user.setBday(bday);
            //user.setPhone(phone_number);
            //user.setCreatedAt(created_at);
            user.setGender(gender_int);
            //user.setEmail(email);
            user.setAge(age);
            UserService k1 = new UserService(userRepository);
            if(k1.saveUser(user) == null) {
                return null;
            }


            HttpServletRequest request1 = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            HttpSession session = request1.getSession();
            session.setAttribute("user", user);

            return KakaoDTO.builder()
                    .name(name)
                    //.phone(phone_number)
                    //.createdAt(created_at)
                    .gender(gender_int)
                    //.email(email)
                    .age(age)
                    .userid(id)
                    //.bday(bday)
                    .build();

        } catch (Exception e) {
            throw new Exception("Failed to retrieve user info from Kakao API", e);
        }
    }

    public void kakaoDisconnect(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                kakaoLogoutRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        System.out.println("반환된 id: "+id);
    }

}
