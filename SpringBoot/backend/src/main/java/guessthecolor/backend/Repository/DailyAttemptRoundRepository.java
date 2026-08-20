package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guessthecolor.backend.Domain.DailyAttemptRound;

@Repository
public interface DailyAttemptRoundRepository extends JpaRepository<DailyAttemptRound, Long>{
    
}
