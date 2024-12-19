package Webprogramming.KOR_Polimeter.web.api.controller;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import Webprogramming.KOR_Polimeter.web.api.service.PoliticianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}


