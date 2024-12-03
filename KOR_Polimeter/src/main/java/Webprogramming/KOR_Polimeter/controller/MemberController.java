package Webprogramming.KOR_Polimeter.controller;

import Webprogramming.KOR_Polimeter.domain.Member;
import Webprogramming.KOR_Polimeter.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/myPage")
    private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        String user_name = "", user_email = "";

        if (member != null) {
            user_name = member.getUsername();
            user_email = member.getEmail();

            session.setAttribute("isLogin", true);
            session.setAttribute("login.name", user_name);
            session.setAttribute("login.email", user_email);
        }

        out.print("<html><body>");
        out.print("안녕하세요 "+ user_name + "님!!!<br>");
        out.print("<a href='show'>회원정보보기</a>");
        out.print("</body></html>");
    }

    @GetMapping("/show")
    protected void doShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = "", email = "";

        HttpSession session = request.getSession();

        name = (String) session.getAttribute("login.name");
        email = (String) session.getAttribute("login.email");

        out.print("<html><body>");
        out.print("이름: " + name + "<br>");
        out.print("이메일: " + email + "<br>");
        out.print("</body></html>");
    }
}
