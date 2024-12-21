package Webprogramming.KOR_Polimeter.web.api.service;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import Webprogramming.KOR_Polimeter.web.api.repository.PoliticianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PoliticianService {

    @Autowired
    private PoliticianRepository repository;

    // 전체 정치인의 투표 수 조회
    public List<Politician> getAllPoliticians() {
        return repository.findAll();
    }

    // 특정 정치인의 투표 수 조회
    public Optional<Politician> getPoliticianById(int id) {
        return repository.findById(id);
    }

    // 정치인 이름 검색 시 정보 조회
    public List<Politician> getPoliticiansByName(String name) {
        return repository.findAllByName(name);
    }

    // 득표수 상위 10명 조회 (총 득표수 포함)
    public Map<String, Object> getTop10Politicians() {
        List<Object[]> results = repository.findTop10PoliticiansByCount();

        // politicians 테이블의 count 값 합산 (총 득표수 계산)
        int totalVotes = repository.sumCount();  // sumCount 메서드를 이용해 totalVotes 계산

        // 상위 10명 결과 준비
        List<Map<String, Object>> topPoliticians = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("id", result[0]);
            data.put("name", result[1]);
            data.put("count", result[2]);
            topPoliticians.add(data);
        }

        // 응답 데이터 준비
        Map<String, Object> response = new HashMap<>();
        response.put("totalVotes", totalVotes); // 총 득표수
        response.put("top10Politicians", topPoliticians); // 상위 10명

        return response;
    }

    // 득표수 상위 10명 조회 (총 득표수 포함)
    public Map<String, Object> getRankPoliticians() {
        List<Object[]> results = repository.findTop300PoliticiansByCount();

        // politicians 테이블의 count 값 합산 (총 득표수 계산)
        int totalVotes = repository.sumCount();  // sumCount 메서드를 이용해 totalVotes 계산

        // 인기순 300명 결과 준비
        List<Map<String, Object>> topPoliticians = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("id", result[0]);
            data.put("name", result[1]);
            data.put("count", result[2]);
            topPoliticians.add(data);
        }

        // 응답 데이터 준비
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalVotes", totalVotes); // 총 득표수
        response.put("top300Politicians", topPoliticians); // 상위 10명

        return response;
    }
}

