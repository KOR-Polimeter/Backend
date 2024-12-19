package Webprogramming.KOR_Polimeter.web.api.service;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import Webprogramming.KOR_Polimeter.web.api.repository.PoliticianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}

