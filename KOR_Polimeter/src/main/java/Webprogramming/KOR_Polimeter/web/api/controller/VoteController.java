package Webprogramming.KOR_Polimeter.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class VoteController {
    @GetMapping("/vote")
    public String redirectToTopPoliticiansPage() {
        return "redirect:/html/vote.html"; // static/html/toppol.html로 리다이렉트
    }
}
