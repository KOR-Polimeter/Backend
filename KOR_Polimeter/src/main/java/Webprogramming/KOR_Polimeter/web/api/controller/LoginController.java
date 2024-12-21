package Webprogramming.KOR_Polimeter.web.api.controller;

import Webprogramming.KOR_Polimeter.web.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class LoginController {

    @GetMapping("/check-login")
    @ResponseBody
    public String checkLoginStatus() {
        HttpServletRequest request1 = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request1.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return "로그인된 사용자: " + user.getName();
            } else {
                return "로그인되지 않았습니다.";
            }
        } else {
            return "세션이 존재하지 않습니다.";
        }
    }
}
