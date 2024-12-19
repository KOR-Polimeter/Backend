package Webprogramming.KOR_Polimeter.web.api.repository;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PoliticianRepository extends JpaRepository<Politician, Integer> {
    Optional<Politician> findById(long id);
    Optional<Politician> findByIdAndName(int id, String name);

}
