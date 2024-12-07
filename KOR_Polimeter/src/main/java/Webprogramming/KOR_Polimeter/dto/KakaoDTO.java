package Webprogramming.KOR_Polimeter.dto;

import Webprogramming.KOR_Polimeter.domain.Member;
import lombok.Builder;
import lombok.Data;

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
