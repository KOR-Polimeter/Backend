package Webprogramming.KOR_Polimeter.repository;

// VotesResultRepository.java
import Webprogramming.KOR_Polimeter.domain.VotesResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotesResultRepo extends JpaRepository<VotesResult, Long> {
    // 특정 정치인에 대한 투표 결과 조회
    VotesResult findByPoliticianId(Long politicianId);
}
