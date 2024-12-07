package Webprogramming.KOR_Polimeter;

import Webprogramming.KOR_Polimeter.dto.KakaoDTO;
import Webprogramming.KOR_Polimeter.service.KakaoService;
import Webprogramming.KOR_Polimeter.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final KakaoService kakaoService;

    @GetMapping("/")
    public String main(Model model) {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=772c65da382eb8de1916b5771770e5e7&redirect_uri=http://localhost:8080/auth/kakao/callback";
        model.addAttribute("kakaoUrl", kakaoUrl);

        return "main";
    }

    @GetMapping("/login")
    public String login(Model model) {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=772c65da382eb8de1916b5771770e5e7&redirect_uri=http://localhost:8080/auth/kakao/callback";
        model.addAttribute("kakaoUrl", kakaoUrl);

        return "login";
    }



    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam String code, Model model) throws Exception {
        try {
            KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(code);
            model.addAttribute("kakaoInfo", kakaoDTO.toString());
            return "main"; // index.html로 이동
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred");
            return "main";
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