package Webprogramming.KOR_Polimeter.web.api.controller;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import Webprogramming.KOR_Polimeter.web.api.service.PoliticianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/politician")
public class ApiPoliticianController {

    @Autowired
    private PoliticianService service;

    // 특정 정치인의 모든 정보 조회 (POST 방식)
    @PostMapping
    public ResponseEntity<?> getPoliticianByPolId(@RequestBody Map<String, Integer> request) {
        Integer polId = request.get("pol_id"); // JSON에서 pol_id 값 추출

        if (polId == null) {
            return ResponseEntity.badRequest().body("Invalid request: pol_id is missing");
        }

        Optional<Politician> politician = service.getPoliticianById(polId);

        if (politician.isPresent()) {
            return ResponseEntity.ok(politician.get()); // Politician 객체 반환
        } else {
            return ResponseEntity.status(404).body("Politician not found"); // 에러 메시지 반환
        }
    }

    // 정치인의 정보를 이름으로 조회 (GET 방식 - /search/name)
    @GetMapping("/search/name")
    public ResponseEntity<?> getPoliticianByName(@RequestParam(required = false) String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("Invalid request: name is missing or empty");
        }

        List<Politician> politicians = service.getPoliticiansByName(name);

        if (politicians.isEmpty()) {
            return ResponseEntity.status(404).body("No politicians found");
        } else {
            return ResponseEntity.ok(politicians); // Politician 리스트 반환
        }
    }

    // 득표수 상위 10명의 정치인 조회
    @GetMapping("/top10")
    public ResponseEntity<?> getTop10Politicians() {
        Map<String, Object> response = service.getTop10Politicians();

        // totalVotes와 topPoliticians가 포함된 응답을 처리
        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(404).body("No politicians found");
        } else {
            return ResponseEntity.ok(response);
        }
    }

    // 전체 정치인 득표수 조회(내림차순)
    @GetMapping("/top300")
    public ResponseEntity<?> findTop300PoliticiansByCount() {
        Map<String, Object> response = service.getRankPoliticians();

        // totalVotes와 topPoliticians가 포함된 응답을 처리
        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(404).body("No politicians found");
        } else {
            return ResponseEntity.ok(response);
        }
    }
}




