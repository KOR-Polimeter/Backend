package Webprogramming.KOR_Polimeter.web.api.controller;

import Webprogramming.KOR_Polimeter.web.api.dto.VoteRequest;
import Webprogramming.KOR_Polimeter.web.api.dto.VoteResponse;
import Webprogramming.KOR_Polimeter.web.api.repository.UserRepository;
import Webprogramming.KOR_Polimeter.web.api.service.UserVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiVoteController {

    @Autowired
    private UserVoteService userVoteService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/vote")
    public ResponseEntity<String> vote(@RequestBody VoteRequest voteRequest) {
        try {
            // 재투표 가능한지 확인
            boolean canVote = userVoteService.canUserVote(voteRequest.getUserId());
            if (!canVote) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot vote again within one month.");
            }

            // 사용자가 여러 정치인에게 투표 처리
            userVoteService.voteForPoliticians(voteRequest.getUserId(), voteRequest.getVotes());

            return ResponseEntity.ok("Votes processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing votes: " + e.getMessage());
        }
    }


    @PostMapping("/myVote")
    public VoteResponse getMyVotes(@RequestBody VoteRequest voteRequest) {
        long userId = voteRequest.getUserId(); // 요청 본문에서 userId 추출
        return userVoteService.getVotesByUser(userId); // 투표한 정치인 정보 반환
    }


}

