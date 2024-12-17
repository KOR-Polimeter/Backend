package Webprogramming.KOR_Polimeter.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "votes_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotesResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // AUTO_INCREMENT 컬럼에 해당하는 id

    @ManyToOne
    @JoinColumn(name = "pol_id", nullable = false)
    private Politician politician; // Politician 엔티티와의 연관관계 설정

    private Integer count; // 카운트 (int 컬럼)
}
