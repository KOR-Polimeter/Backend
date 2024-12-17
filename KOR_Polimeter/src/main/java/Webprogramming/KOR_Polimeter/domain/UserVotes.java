package Webprogramming.KOR_Polimeter.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // AUTO_INCREMENT 컬럼에 해당하는 id

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 엔티티와의 연관관계 설정

    @ManyToOne
    @JoinColumn(name = "pol_id", nullable = false)
    private Politician politician; // Politician 엔티티와의 연관관계 설정

    private LocalDateTime voteDate; // 투표 날짜 (datetime 컬럼)
}
