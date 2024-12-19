package Webprogramming.KOR_Polimeter.web.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_votes")
@Getter
@Setter
public class UserVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 투표 ID (Primary Key)

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user; // User 엔티티와의 관계

    @ManyToOne
    @JoinColumn(name = "pol_id", referencedColumnName = "id", nullable = false)
    private Politician politician; // Politician 엔티티와의 관계

    private LocalDateTime voteDate; // 투표 날짜
}
