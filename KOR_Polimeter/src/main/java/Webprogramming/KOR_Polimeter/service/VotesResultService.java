package Webprogramming.KOR_Polimeter.service;

import Webprogramming.KOR_Polimeter.domain.VotesResult;
import Webprogramming.KOR_Polimeter.repository.VotesResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotesResultService {

    @Autowired
    private VotesResultRepo votesResultRepo;

    // 특정 정치인에 대한 투표 결과 조회
    public VotesResult getVotesResult(Long politicianId) {
        return votesResultRepo.findByPoliticianId(politicianId);
    }
}
