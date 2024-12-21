package Webprogramming.KOR_Polimeter.controller;

import Webprogramming.KOR_Polimeter.web.api.dto.KakaoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    @GetMapping("/vote")
    public String redirectToVotePage(HttpSession session, Model model) {
        Object user = session.getAttribute("user"); // 세션에서 사용자 정보 가져오기

        // 사용자 정보가 존재하면 모델에 추가
        if (user != null) {
            model.addAttribute("isLoggedIn", true); // 로그인 상태
            model.addAttribute("user", user);      // 사용자 정보
        } else {
            model.addAttribute("isLoggedIn", false); // 비로그인 상태
        }

        return "vote"; // vote.html 반환
    }


    @GetMapping("/top10")
    public String redirectToTopPoliticiansPage(HttpSession session, Model model) {
        Object user = session.getAttribute("user"); // 세션에서 사용자 정보 가져오기

        // 사용자 정보가 존재하면 모델에 추가
        if (user != null) {
            model.addAttribute("isLoggedIn", true); // 로그인 상태
            model.addAttribute("user", user);      // 사용자 정보
        } else {
            model.addAttribute("isLoggedIn", false); // 비로그인 상태
        }

        return "toppol"; // static/html/toppol.html로 리다이렉트
    }

    @GetMapping("/voteresult")
    public String redirectToVoteResultPage(HttpSession session, Model model) {
        Object user = session.getAttribute("user"); // 세션에서 사용자 정보 가져오기

        // 사용자 정보가 존재하면 모델에 추가
        if (user != null) {
            model.addAttribute("isLoggedIn", true); // 로그인 상태
            model.addAttribute("user", user);      // 사용자 정보
        } else {
            model.addAttribute("isLoggedIn", false); // 비로그인 상태
        }

        return "vote_result";
    }

}




