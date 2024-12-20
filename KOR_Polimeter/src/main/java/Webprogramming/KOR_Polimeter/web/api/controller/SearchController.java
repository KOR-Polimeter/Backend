package Webprogramming.KOR_Polimeter.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Webprogramming.KOR_Polimeter.web.api.service.SearchService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class SearchController {

    private final SearchService searchService;

    // 생성자를 통한 의존성 주입
    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/searching")
    public String showSearchingPage() {
        return "searching"; // searching.html로 연결
    }

    @GetMapping("/details")
    public String details(
            @RequestParam("id") int itemId,
            @RequestParam("name") String name,
            @RequestParam("party") String party,
            @RequestParam("region") String region,
            @RequestParam("bday") String bday,
            @RequestParam("age") int age,
            @RequestParam("description") String description,
            @RequestParam("gender") String gender,
            Model model) {

        // 모델에 데이터 추가
        model.addAttribute("itemId", itemId);
        model.addAttribute("name", name);
        model.addAttribute("party", party);
        model.addAttribute("region", region);
        model.addAttribute("bday", bday);
        model.addAttribute("age", age);
        model.addAttribute("description", description);
        model.addAttribute("gender", gender);

        // details.html로 반환
        return "details";
    }


    @PostMapping("/searching")
    public String handleSearch(@RequestParam String query, Model model) {
        // 검색 로직 처리
        model.addAttribute("results", searchService.search(query)); // searchService를 통해 호출
        return "searchResults"; // 검색 결과를 보여줄 뷰 이름
    }
}
