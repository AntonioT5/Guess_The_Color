package guessthecolor.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guessthecolor.backend.Domain.Stats;
import guessthecolor.backend.Domain.User;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long>{
    
    Optional<Stats> findByUser(User user);
}
