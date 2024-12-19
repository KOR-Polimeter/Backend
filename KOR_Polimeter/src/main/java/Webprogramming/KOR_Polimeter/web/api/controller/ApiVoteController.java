package Webprogramming.KOR_Polimeter.web.api.controller;

import Webprogramming.KOR_Polimeter.web.api.dto.VoteRequest;
import Webprogramming.KOR_Polimeter.web.api.model.User;
import Webprogramming.KOR_Polimeter.web.api.service.UserVoteService;
import Webprogramming.KOR_Polimeter.web.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}


//package Webprogramming.KOR_Polimeter.web.api.controller;
//
//import Webprogramming.KOR_Polimeter.web.api.dto.VoteRequest;
//import Webprogramming.KOR_Polimeter.web.api.model.User;
//import Webprogramming.KOR_Polimeter.web.api.model.Politician;
//import Webprogramming.KOR_Polimeter.web.api.service.UserVoteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/vote")
//public class ApiVoteController {
//
//    @Autowired
//    private UserVoteService userVoteService;
//
//    @PostMapping
//    public String vote(@RequestBody VoteRequest voteRequest) {
//        // 클라이언트로부터 받은 데이터 (userId, polId)
//        User user = new User();
//        user.setId(voteRequest.getUserId());
//        Politician politician = new Politician();
//        politician.setId(voteRequest.getPolId());
//
//        // 사용자 투표 가능 여부 확인
//        if (userVoteService.canUserVote(user)) {
//            userVoteService.voteForPolitician(user, politician); // 투표 처리
//            return "Vote successful!";
//        } else {
//            return "You cannot vote again within one month!";
//        }
//    }
//}
