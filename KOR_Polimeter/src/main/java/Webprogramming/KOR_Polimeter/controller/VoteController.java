package Webprogramming.KOR_Polimeter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class VoteController {

    @GetMapping("/vote")
    public String getVotePage(Model model) {
        // 서버에서 정당 및 후보자 데이터를 생성
        List<Map<String, Object>> voteData = List.of(
                Map.of(
                        "party", "더불어민주당",
                        "members", 171,
                        "candidates", List.of(
                                Map.of("id", "1001", "name", "이재명", "image", "/css/이재명.jpg"),
                                Map.of("id", "1002", "name", "이재명", "image", "/css/이재명.jpg")
                        )
                ),
                Map.of(
                        "party", "국민의힘",
                        "members", 108,
                        "candidates", List.of(
                                Map.of("id", "2001", "name", "추경호", "image", "/css/추경호.png"),
                                Map.of("id", "2002", "name", "추경호", "image", "/css/추경호.png")
                        )
                )
        );

        // 모델에 데이터 추가
        model.addAttribute("voteData", voteData);

        return "vote"; // Thymeleaf 템플릿 이름
    }
}
