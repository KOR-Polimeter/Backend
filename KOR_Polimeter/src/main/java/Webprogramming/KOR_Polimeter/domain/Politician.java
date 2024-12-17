package Webprogramming.KOR_Polimeter.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "politicians")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Politician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // AUTO_INCREMENT 컬럼에 해당하는 id
    private String name;
    private String party;
    private String region;
    private LocalDateTime bday;
    private Integer gender;
    private String description;
    private Integer age;
    private Integer count;
}
