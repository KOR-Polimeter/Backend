/*package Webprogramming.KOR_Polimeter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 명시적으로 설정
    @Column(name = "id") // AUTO_INCREMENT 컬럼에 해당하는 id
    private String name; // 이름
    private LocalDateTime bday; // 생일 (datetime 컬럼)
    private String phone; // 전화번호
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 생성일시 (datetime 컬럼)
    private Integer gender; // 성별 (int 컬럼)
}
 */