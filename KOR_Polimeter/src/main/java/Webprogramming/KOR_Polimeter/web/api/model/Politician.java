package Webprogramming.KOR_Polimeter.web.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "politicians")
@Getter
@Setter
public class Politician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 정치인 ID (Primary Key)

    private String name;
    private String party;
    private String region;
    private LocalDateTime bday;
    private int gender;
    private String description;
    private int age;
    private int count; // 투표 수
}
