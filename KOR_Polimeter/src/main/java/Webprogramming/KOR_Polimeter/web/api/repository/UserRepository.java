package Webprogramming.KOR_Polimeter.web.api.repository;

//import Webprogramming.KOR_Polimeter.domain.Member;
import Webprogramming.KOR_Polimeter.web.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    boolean existsByUserId(Long Userid);
    // userid를 기준으로 id(기본키) 조회
    @Query("SELECT u.id FROM User u WHERE u.userId = :userid")
    int findIdByUserId(@Param("userid") long userid);
}