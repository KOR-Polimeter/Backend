package Webprogramming.KOR_Polimeter.service;

import Webprogramming.KOR_Polimeter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
}
