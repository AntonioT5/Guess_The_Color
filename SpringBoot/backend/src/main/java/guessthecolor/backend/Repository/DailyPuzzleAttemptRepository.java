package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guessthecolor.backend.Domain.DailyPuzzleAttempt;

public interface DailyPuzzleAttemptRepository extends JpaRepository<DailyPuzzleAttempt, Long>{
    
}
