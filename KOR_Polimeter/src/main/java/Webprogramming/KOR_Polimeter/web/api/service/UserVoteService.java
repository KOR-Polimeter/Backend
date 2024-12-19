package Webprogramming.KOR_Polimeter.web.api.service;

import Webprogramming.KOR_Polimeter.web.api.dto.VoteRequest;
import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import Webprogramming.KOR_Polimeter.web.api.model.User;
import Webprogramming.KOR_Polimeter.web.api.model.UserVote;
import Webprogramming.KOR_Polimeter.web.api.repository.UserRepository;
import Webprogramming.KOR_Polimeter.web.api.repository.UserVoteRepository;
import Webprogramming.KOR_Polimeter.web.api.repository.PoliticianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserVoteService {

    @Autowired
    private UserVoteRepository userVoteRepository;

    @Autowired
    private PoliticianRepository politicianRepository;

    @Autowired
    private UserRepository userRepository; // UserRepository 추가

    /**
     * 사용자가 한 달 이내에 투표했는지 확인
     */
    public boolean canUserVote(int userId) {
        // user_votes 테이블에서 해당 사용자의 마지막 투표 기록 조회
        Optional<UserVote> lastVote = userVoteRepository.findLastVoteByUser(userId);

        if (lastVote.isPresent()) {
            // 마지막 투표 날짜 가져오기
            LocalDateTime lastVoteDate = lastVote.get().getVoteDate();

            // 현재 날짜 기준으로 1개월 경과 여부 확인
            LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
            System.out.println("1개월"+oneMonthAgo.toString());

            if (lastVoteDate.isAfter(oneMonthAgo)) {
                return false; // 1개월이 지나지 않았다면 재투표 불가
            }
        }

        return true; // 투표 가능
    }

    /**
     * 여러 정치인에게 투표 처리
     */
    @Transactional
    public void voteForPoliticians(int userId, List<VoteRequest.Vote> votes) {
        // User 객체를 userId로 조회
        System.out.println("user아이디"+userId);
        User user = userRepository.findById((long) userId).orElseThrow(() -> new RuntimeException("User not found"));

        for (VoteRequest.Vote vote : votes) {
            // 정치인 ID로 정치인 조회
            Politician politician = politicianRepository.findById(vote.getPolId()).orElseThrow(() -> new RuntimeException("Politician not found"));

            // 새 투표 기록 생성
            UserVote userVote = new UserVote();
            userVote.setUser(user);
            userVote.setPolitician(politician);
            userVote.setVoteDate(LocalDateTime.now()); // 현재 시간 설정

            // 투표 기록 저장
            userVoteRepository.save(userVote);

            // 정치인 ID를 통해 다시 조회
            Politician existingPolitician = politicianRepository.findById(politician.getId()).orElseThrow(() -> new RuntimeException("Politician not found"));

            // 정치인의 투표 수 증가
            existingPolitician.setCount(existingPolitician.getCount() + 1);

            // 정치인 객체 저장
            politicianRepository.save(existingPolitician);
        }
    }
}
