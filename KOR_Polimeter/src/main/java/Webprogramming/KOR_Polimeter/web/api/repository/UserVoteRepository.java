package Webprogramming.KOR_Polimeter.web.api.repository;

import Webprogramming.KOR_Polimeter.web.api.model.Politician;
import Webprogramming.KOR_Polimeter.web.api.model.User;
import Webprogramming.KOR_Polimeter.web.api.model.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, Integer> {
    @Query("SELECT uv FROM UserVote uv WHERE uv.user.id = :userId ORDER BY uv.voteDate DESC limit 1")
    Optional<UserVote> findLastVoteByUser(@Param("userId") int userId);
    Optional<UserVote> findByUserAndPolitician(User user, Politician politician);
}
