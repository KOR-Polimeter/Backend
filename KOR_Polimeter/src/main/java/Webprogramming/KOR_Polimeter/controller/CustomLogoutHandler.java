package Webprogramming.KOR_Polimeter.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 인증 정보 초기화 또는 로그아웃 관련 로직 구현
        System.out.println("Custom logout handler executed. User is logged out.");
    }
}

