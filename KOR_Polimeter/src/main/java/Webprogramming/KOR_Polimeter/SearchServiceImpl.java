package Webprogramming.KOR_Polimeter;

import Webprogramming.KOR_Polimeter.web.api.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public List<String> search(String query) {
        // 실제 검색 로직 구현 (예: DB 조회, 외부 API 호출 등)
        // 여기서는 간단한 예제로 더미 데이터를 반환
        List<String> results = new ArrayList<>();
        results.add("검색 결과 1: " + query);
        results.add("검색 결과 2: " + query);
        results.add("검색 결과 3: " + query);
        return results;
    }
}