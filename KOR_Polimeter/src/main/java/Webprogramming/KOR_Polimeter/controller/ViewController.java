package Webprogramming.KOR_Polimeter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

//    @GetMapping("/")
//    public String redirectToMainPage() {
//        return "redirect:/html/main.html";
//    }
//
//    @GetMapping("/login")
//    public String redirectToLoginPage() {
//        return "redirect:/html/login.html";
//    }

    @GetMapping("/vote")
    public String redirectToVotePage() {
        return "vote"; // static/html/toppol.html로 리다이렉트
    }

//    @GetMapping("/toppoliticians")
//    public String redirectToTopPoliticiansPage() {
//        return "redirect:/html/toppol.html"; // static/html/toppol.html로 리다이렉트
//    }

}


