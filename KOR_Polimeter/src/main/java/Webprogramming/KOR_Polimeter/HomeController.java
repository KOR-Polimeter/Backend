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
    @GetMapping("/")
    public String main() {
        return "main";
    }
}