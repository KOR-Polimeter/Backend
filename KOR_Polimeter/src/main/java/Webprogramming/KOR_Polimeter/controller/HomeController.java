package Webprogramming.KOR_Polimeter.controller;

import Webprogramming.KOR_Polimeter.web.api.dto.KakaoDTO;
import Webprogramming.KOR_Polimeter.web.api.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final KakaoService kakaoService;

    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=772c65da382eb8de1916b5771770e5e7&redirect_uri=http://localhost:8080/auth/kakao/callback";
        model.addAttribute("kakaoUrl", kakaoUrl);

        Object user = session.getAttribute("user"); // "user"는 세션에 저장된 사용자 정보 키

        // 사용자 정보가 존재하면 모델에 추가
        if (user != null) {
            model.addAttribute("isLoggedIn", true); // 로그인 상태
            model.addAttribute("user", user);      // 사용자 정보
        } else {
            model.addAttribute("isLoggedIn", false); // 비로그인 상태
        }

        return "main";
    }

//    @GetMapping("/login")
//    public String login(Model model) {
//        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=772c65da382eb8de1916b5771770e5e7&redirect_uri=http://localhost:8080/auth/kakao/callback";
//        model.addAttribute("kakaoUrl", kakaoUrl);
//
//        return "login";
//    }


    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam String code, HttpSession session) throws Exception {
        try {

            // 카카오 API를 통해 사용자 정보 가져오기
            KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(code);

            // 세션에 사용자 정보 저장
            session.setAttribute("user", kakaoDTO);

            // 메인 페이지로 리다이렉트
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();

            // 에러 발생 시 에러 메시지를 세션에 저장 (필요한 경우)
            session.setAttribute("error", "An error occurred");

            // 메인 페이지로 리다이렉트
            return "redirect:/";
        }
    }

    /*@GetMapping("/logout")
    public String logout(HttpSession session) {
        KakaoService.logoutUser(session.getId());
        session.removeAttribute("kakaoInfo");
        session.invalidate();
        return "redirect:/index"; // 로그아웃 후 메인 페이지로 리다이렉트
    }*/

    @GetMapping("/logout")
    public String kakaoLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String accessToken = oidcUser.getIdToken().getTokenValue(); // 액세스 토큰 가져오기

            // 카카오 로그아웃 API 호출
            String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.postForEntity(kakaoLogoutUrl, null, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Spring Security 로그아웃 처리
        request.getSession().invalidate();
        return "redirect:/";
    }
}