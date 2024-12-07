package Webprogramming.KOR_Polimeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import Webprogramming.KOR_Polimeter.service.SearchService;

import java.util.List;

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

    @PostMapping("/searching")
    public String handleSearch(@RequestParam String query, Model model) {
        // 검색 로직 처리
        model.addAttribute("results", searchService.search(query)); // searchService를 통해 호출
        return "searchResults"; // 검색 결과를 보여줄 뷰 이름
    }
}
