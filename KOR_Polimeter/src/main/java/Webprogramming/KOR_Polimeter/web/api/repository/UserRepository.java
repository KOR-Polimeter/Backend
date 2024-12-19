package Webprogramming.KOR_Polimeter.web.api.repository;

import Webprogramming.KOR_Polimeter.web.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
