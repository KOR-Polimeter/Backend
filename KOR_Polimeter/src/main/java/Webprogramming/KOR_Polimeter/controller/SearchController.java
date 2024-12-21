package Webprogramming.KOR_Polimeter.controller;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Webprogramming.KOR_Polimeter.web.api.service.SearchService;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpSession;

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
    public String showSearchingPage(HttpSession session, Model model) {
        Object user = session.getAttribute("user"); // 세션에서 사용자 정보 가져오기

        // 사용자 정보가 존재하면 모델에 추가
        if (user != null) {
            model.addAttribute("isLoggedIn", true); // 로그인 상태
            model.addAttribute("user", user);      // 사용자 정보
        } else {
            model.addAttribute("isLoggedIn", false); // 비로그인 상태
        }
        return "searching"; // searching.html로 연결
    }

    @GetMapping("/details")
    public String details(@RequestParam("name") String name, HttpSession session, Model model) {

        Object user = session.getAttribute("user"); // 세션에서 사용자 정보 가져오기

        // 사용자 정보가 존재하면 모델에 추가
        if (user != null) {
            model.addAttribute("isLoggedIn", true); // 로그인 상태
            model.addAttribute("user", user);      // 사용자 정보
        } else {
            model.addAttribute("isLoggedIn", false); // 비로그인 상태
        }

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // /searching/name API 호출 URL
        String url = "http://localhost:8080/api/politician/search/name?name=" + name;

        // API 호출 및 Politician 리스트 받아오기
        ResponseEntity<List<Politician>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Politician>>() {}
        );

        // Politician 리스트 가져오기
        List<Politician> politicians = response.getBody();

        if (politicians == null || politicians.isEmpty()) {
            // 검색 결과가 없을 경우 처리
            model.addAttribute("message", "검색 결과가 없습니다.");
            return "error"; // 에러 페이지로 이동
        }

        // 유일한 정치인 데이터 가져오기 (첫 번째 정치인)
        Politician politician = politicians.get(0);
        System.out.println(politician.getDescription() + " 데이터를 가져왔어요"); // 로그 확인용
        // 모델에 정치인 데이터 추가
        model.addAttribute("itemId", politician.getId());
        model.addAttribute("name", politician.getName());
        model.addAttribute("party", politician.getParty());
        model.addAttribute("region", politician.getRegion());
        model.addAttribute("bday", politician.getBday());
        model.addAttribute("age", politician.getAge());
        model.addAttribute("description", politician.getDescription());
        model.addAttribute("gender", politician.getGender());

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