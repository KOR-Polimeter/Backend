package Webprogramming.KOR_Polimeter.dto;

import Webprogramming.KOR_Polimeter.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class KakaoDTO {
    private Long id; // AUTO_INCREMENT 컬럼에 해당하는 id
    private String name; // 이름
    private String bday; // 생일 (datetime 컬럼)
    private String phone; // 전화번호
    private String createdAt; // 생성일시 (datetime 컬럼)
    private int age;
    private Integer gender; // 성별 (int 컬럼)
    private String email;

    public static User convertToUser(KakaoDTO kakaoDTO) {
        User user = new User();
        user.setId(kakaoDTO.getId());
        user.setName(kakaoDTO.getName());
        user.setCreatedAt(kakaoDTO.getCreatedAt());
        user.setPhone(kakaoDTO.getPhone());
        user.setGender(kakaoDTO.getGender());
        user.setEmail(kakaoDTO.getEmail());
        user.setAge(kakaoDTO.getAge());
        user.setBday(kakaoDTO.getBday());
        return user;
    }
}