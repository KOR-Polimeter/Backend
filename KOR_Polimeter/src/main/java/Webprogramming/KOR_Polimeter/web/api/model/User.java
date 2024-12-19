package Webprogramming.KOR_Polimeter.web.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 사용자 ID (Primary Key)

    private String name;
//    private LocalDateTime bday; // 생일
//    private String phone;
//    private LocalDateTime createdAt; // 가입일
    private int gender;
//    private String email;
    private int age;
}
