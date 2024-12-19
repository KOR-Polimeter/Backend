package Webprogramming.KOR_Polimeter.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Webprogramming.KOR_Polimeter.web.api.service.SearchService;

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
    public String details(@RequestParam("id") int itemId, Model model) {
        System.out.println("Received itemId: " + itemId);
        model.addAttribute("itemId", itemId);
        return "details";
    }

    @PostMapping("/searching")
    public String handleSearch(@RequestParam String query, Model model) {
        // 검색 로직 처리
        model.addAttribute("results", searchService.search(query)); // searchService를 통해 호출
        return "searchResults"; // 검색 결과를 보여줄 뷰 이름
    }
}
