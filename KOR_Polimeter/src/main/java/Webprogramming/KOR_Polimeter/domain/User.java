package Webprogramming.KOR_Polimeter.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // AUTO_INCREMENT 컬럼에 해당하는 id
    private String name; // 이름
    private LocalDateTime bday; // 생일 (datetime 컬럼)
    private String phone; // 전화번호
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 생성일시 (datetime 컬럼)
    private Integer gender; // 성별 (int 컬럼)
    private Integer age; // 나이 (int 컬럼)
}
