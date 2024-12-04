package Webprogramming.KOR_Polimeter;

import Webprogramming.KOR_Polimeter.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final KakaoService kakaoService;

    @GetMapping("/")
    public String login(Model model) {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?client_id=772c65da382eb8de1916b5771770e5e7&redirect_uri=http://localhost:8080/login/oauth2/code/kakao&response_type=code";
        model.addAttribute("kakaoUrl", kakaoUrl);

        return "index";
    }
}
