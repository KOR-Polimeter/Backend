package Webprogramming.KOR_Polimeter.web.api.service;

import Webprogramming.KOR_Polimeter.web.api.dto.VoteRequest;
import Webprogramming.KOR_Polimeter.web.api.dto.VoteResponse;
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
import java.util.ArrayList;
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
    public boolean canUserVote(long userId) {
        // userId로 User 객체 조회
        User user = userRepository.findByUserId(userId);
        int userIdFromDb = user.getId(); // userId로 User 객체의 id를 가져옵니다.

        // user_votes 테이블에서 해당 사용자의 마지막 투표 기록 조회
        Optional<UserVote> lastVote = userVoteRepository.findLastVoteByUser(userIdFromDb);

        if (lastVote.isPresent()) {
            // 마지막 투표 날짜 가져오기
            LocalDateTime lastVoteDate = lastVote.get().getVoteDate();

            // 현재 날짜 기준으로 1개월 경과 여부 확인
            LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
            System.out.println("1개월 전 날짜: " + oneMonthAgo.toString());

            // 1개월 이내에 투표한 경우, 재투표 불가
            if (lastVoteDate.isAfter(oneMonthAgo)) {
                return false; // 1개월이 지나지 않았다면 재투표 불가
            }
        }

        return true; // 1개월이 지나면 투표 가능
    }

    /**
     * 여러 정치인에게 투표 처리
     */
    @Transactional
    public void voteForPoliticians(Long userId, List<VoteRequest.Vote> votes) {
        // User 객체를 userId로 조회
        System.out.println("user 아이디: " + userId);
        User user = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 각 투표 요청에 대해 처리
        for (VoteRequest.Vote vote : votes) {
            // 정치인ID로 정치인 조회
            Politician politician = politicianRepository.findById(vote.getPolId())
                    .orElseThrow(() -> new RuntimeException("Politician not found"));

            // 사용자가 이미 해당 정치인에게 투표했는지 확인
            Optional<UserVote> existingVote = userVoteRepository.findByUserAndPolitician(user, politician);

            // 이미 투표한 경우, 중복 투표 처리 방지
            if (existingVote.isPresent()) {
                System.out.println("사용자가 이미 정치인 " + politician.getName() + "에게 투표한 기록이 있습니다.");
                continue; // 중복 투표는 처리하지 않고 넘어갑니다.
            }

            // 새 투표 기록 생성
            UserVote userVote = new UserVote();
            userVote.setUser(user);
            userVote.setPolitician(politician);
            userVote.setVoteDate(LocalDateTime.now()); // 현재 시간 설정

            // 투표 기록 저장
            userVoteRepository.save(userVote);

            // 정치인의 투표 수 증가
            politician.setCount(politician.getCount() + 1);
            politicianRepository.save(politician); // 정치인 객체 저장
        }
    }

    /**
     * 사용자가 투표한 정치인 정보를 반환하는 메소드
     */
    @Transactional
    public VoteResponse getVotesByUser(long userId) {
        // 사용자가 투표한 기록을 조회
        List<UserVote> userVotes = userVoteRepository.findByUserId(userId);
        List<VoteResponse.Vote> voteList = new ArrayList<>();

        // 사용자 투표 기록을 기반으로 정치인 정보 리스트 구성
        for (UserVote userVote : userVotes) {
            Politician politician = userVote.getPolitician();

            // VoteResponse에 담을 데이터 설정
            VoteResponse.Vote vote = new VoteResponse.Vote();
            vote.setPolId(politician.getId());
            vote.setName(politician.getName());
            vote.setParty(politician.getParty());

            // 리스트에 추가
            voteList.add(vote);
        }

        // 응답 객체 생성
        VoteResponse voteResponse = new VoteResponse();
        voteResponse.setUserId(userId);
        voteResponse.setVotes(voteList);

        return voteResponse;
    }
}
