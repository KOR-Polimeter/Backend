package Webprogramming.KOR_Polimeter;

import Webprogramming.KOR_Polimeter.dto.KakaoDTO;
import Webprogramming.KOR_Polimeter.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final KakaoService kakaoService;

    @GetMapping("/")
    public String login(Model model) {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=772c65da382eb8de1916b5771770e5e7&redirect_uri=http://localhost:8080/auth/kakao/callback";
        model.addAttribute("kakaoUrl", kakaoUrl);

        return "index";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam String code, Model model) throws Exception {
        try {
            KakaoDTO kakaoDTO = kakaoService.getKakaoInfo(code);
            model.addAttribute("kakaoInfo", kakaoDTO.toString());
            return "index"; // index.html로 이동
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred");
            return "index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        KakaoService.logoutUser(session.getId());
        session.removeAttribute("kakaoInfo");
        session.invalidate();
        return "redirect:/"; // 로그아웃 후 메인 페이지로 리다이렉트
    }
}
