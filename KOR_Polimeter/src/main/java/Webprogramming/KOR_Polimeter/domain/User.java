package Webprogramming.KOR_Polimeter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // AUTO_INCREMENT 컬럼에 해당하는 id
        private String name; // 이름
        private String bday; // 생일 (datetime 컬럼으로 하려했지만 kakao api에서 받는 값이
                                    // MMDD형식이므로 String으로 변경)
        private int age; // 나이
        private String phone; // 전화번호
    @Column(name = "created_at")
        private String createdAt; // 생성일시 (datetime 컬럼, kakao api에서 connected_at로부터 값을 받음)
        private Integer gender; // 성별 (int 컬럼)
        private String email;
}