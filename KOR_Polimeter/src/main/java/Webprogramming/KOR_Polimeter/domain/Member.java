package Webprogramming.KOR_Polimeter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 명시적으로 설정
    @Column(name = "member_id")
    private long member_id;

    private String username;
    private String email;
}