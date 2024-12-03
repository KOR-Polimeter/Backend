package Webprogramming.KOR_Polimeter.service;

import Webprogramming.KOR_Polimeter.domain.Member;
import Webprogramming.KOR_Polimeter.dto.KakaoDTO;
import Webprogramming.KOR_Polimeter.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class KakaoService {
    @Autowired
    private final MemberRepository memberRepository;

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";

    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    } // https://kauth.kakao.com/pauth/authorize?client_id=KAKAO_CLIENT_ID&redirect_uri=KAKAO_REDIRECT_URL&response_type=code

    public KakaoDTO getKakaoInfo(String code) throws Exception {
        if (code == null) throw new Exception("Fail get authorization code");

        String accessToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", KAKAO_CLIENT_ID);
            params.add("client_secret", KAKAO_CLIENT_SECRET);
            params.add("code", code);
            params.add("redirect_uri", KAKAO_REDIRECT_URL);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
            accessToken = (String) jsonObj.get("access_token");
        } catch (Exception e) {
            throw new Exception("API call failed");
        }
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
            JSONObject account = (JSONObject) jsonObj.get("account");
            JSONObject profile = (JSONObject) jsonObj.get("profile");
            // 사용자 정보 파싱
            long id = (long) jsonObj.get("id"); // 사용자 고유 ID
            String email = String.valueOf(account.get("email"));
            String nickname = String.valueOf(account.get("nickname"));

            Member member = new Member();
            member.setEmail(email);
            member.setUsername(nickname);
            memberRepository.save(member);

            HttpServletRequest request1 = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            HttpSession session = request1.getSession();
            session.setAttribute("member", member);



            // KakaoDTO 빌더 패턴으로 객체 생성
            return KakaoDTO.builder()
                    .id(id)
                    .email(email)
                    .nickname(nickname)
                    .build();

        } catch (Exception e) {
            throw new Exception("Failed to retrieve user info from Kakao API", e);
        }
    }
}
