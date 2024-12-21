package Webprogramming.KOR_Polimeter.web.api.repository;

//import Webprogramming.KOR_Polimeter.domain.Member;
import Webprogramming.KOR_Polimeter.web.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    boolean existsByUserId(Long Userid);
}