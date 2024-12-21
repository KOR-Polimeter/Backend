package Webprogramming.KOR_Polimeter.web.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TopPolViewController {

    @GetMapping("/toppoliticians")
    public String redirectToTopPoliticiansPage() {
        return "redirect:/html/toppol.html"; // static/html/toppol.html로 리다이렉트
    }
}


