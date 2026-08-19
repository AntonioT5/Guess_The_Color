package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guessthecolor.backend.Domain.DailyAttemptRound;

public interface DailyAttemptRoundRepository extends JpaRepository<DailyAttemptRound, Long>{
    
}
