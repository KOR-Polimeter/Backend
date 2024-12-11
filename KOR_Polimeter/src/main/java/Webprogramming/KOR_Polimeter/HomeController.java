package Webprogramming.KOR_Polimeter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class HomeController {
    @GetMapping("/")
    public String main() {
        return "main";
    }
}