/*package Webprogramming.KOR_Polimeter.service;

import Webprogramming.KOR_Polimeter.domain.Member;
import Webprogramming.KOR_Polimeter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member) {
        System.out.println(member + "가 DB에 저장되었습니다.");
        return memberRepository.save(member);
    }
}*/