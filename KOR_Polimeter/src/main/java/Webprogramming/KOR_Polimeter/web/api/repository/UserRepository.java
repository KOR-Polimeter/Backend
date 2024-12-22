package Webprogramming.KOR_Polimeter.web.api.repository;

import Webprogramming.KOR_Polimeter.web.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // User 엔티티를 저장하는 메서드
    User save(User user);

    // userId로 User를 조회하는 메서드
    User findByUserId(Long userId);

    // userId가 존재하는지 확인하는 메서드
    boolean existsByUserId(Long userId);
}
