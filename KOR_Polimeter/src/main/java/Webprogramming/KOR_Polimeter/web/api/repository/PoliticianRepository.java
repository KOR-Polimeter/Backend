package Webprogramming.KOR_Polimeter.web.api.repository;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PoliticianRepository extends JpaRepository<Politician, Integer> {
    Optional<Politician> findById(int id);
    Optional<Politician> findByIdAndName(int id, String name);
    List<Politician> findAllByName(String name);
}
