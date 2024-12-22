package Webprogramming.KOR_Polimeter.controller;

import Webprogramming.KOR_Polimeter.web.api.dto.KakaoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/api/session")
    public String getUserFromSession(HttpSession session, Model model) {
        Object user = session.getAttribute("user");

        if (user != null) {
            // 세션에 저장된 user 정보를 모델에 추가
            model.addAttribute("user", user);
            System.out.println("세션"+user);
            return "User is: " + user.toString(); // 혹은 다른 형식으로 사용자 정보를 반환
        } else {
            return "No user found in session."; // 세션에 user 정보가 없는 경우
        }
    }
}


//package Webprogramming.KOR_Polimeter.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import Webprogramming.KOR_Polimeter.web.api.dto.KakaoDTO;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@RestController
//public class SessionController {
//
//    @GetMapping("/session")
//    public String getUserFromSession(HttpSession session, Model model) {
//        Object user = session.getAttribute("user");
//        System.out.println("세션"+user);
//
//        if (user != null) {
//            // 세션에 저장된 user 정보를 모델에 추가
//            model.addAttribute("user", user);
//            System.out.println("세션"+user);
//            return "User is: " + usertoString(); // 혹은 다른 형식으로 사용자 정보를 반환
//        } else {
//            return "No user found in session."; // 세션에 user 정보가 없는 경우
//        }
//    }
//}
