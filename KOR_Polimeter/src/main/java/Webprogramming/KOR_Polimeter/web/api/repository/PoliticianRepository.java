package Webprogramming.KOR_Polimeter.web.api.repository;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PoliticianRepository extends JpaRepository<Politician, Integer> {
    Optional<Politician> findById(int id);
    Optional<Politician> findByIdAndName(int id, String name);
    List<Politician> findAllByName(String name);

    @Query(value = "SELECT p.id, p.name, p.count FROM politicians p ORDER BY p.count DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10PoliticiansByCount();
    @Query("SELECT SUM(p.count) FROM Politician p")
    int sumCount();
    @Query(value = "SELECT p.id, p.name, p.count FROM politicians p ORDER BY p.count DESC", nativeQuery = true)
    List<Object[]> findTop300PoliticiansByCount();
}
