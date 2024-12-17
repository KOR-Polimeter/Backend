package Webprogramming.KOR_Polimeter.controller;

import Webprogramming.KOR_Polimeter.domain.VotesResult;
import Webprogramming.KOR_Polimeter.dto.VoteRequest;
import Webprogramming.KOR_Polimeter.service.VotesResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VotesResultController {

    @Autowired
    private VotesResultService votesResultService;

    // POST 방식으로 특정 정치인에 대한 투표 결과 조회 API
    @PostMapping("/result")
    public VotesResult getVotesResult(@RequestBody VoteRequest request) {
        return votesResultService.getVotesResult(request.getPoliticianId());
    }
}

