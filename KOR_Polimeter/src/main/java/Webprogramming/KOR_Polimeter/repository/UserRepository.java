package Webprogramming.KOR_Polimeter.repository;

//import Webprogramming.KOR_Polimeter.domain.Member;
import Webprogramming.KOR_Polimeter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
}