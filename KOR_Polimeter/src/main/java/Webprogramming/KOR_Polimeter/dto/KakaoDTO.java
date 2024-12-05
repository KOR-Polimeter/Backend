package Webprogramming.KOR_Polimeter.dto;

import lombok.Builder;
import lombok.Data;

import Webprogramming.KOR_Polimeter.domain.Member;

@Builder
@Data
public class KakaoDTO {
    private long id;
    private String email;
    private String nickname;

    public static Member convertToMember(KakaoDTO kakaoDTO) {
        Member member = new Member();
        member.setMember_id(kakaoDTO.getId());
        member.setEmail(kakaoDTO.getEmail());
        member.setUsername(kakaoDTO.getNickname());
        return member;
    }
}