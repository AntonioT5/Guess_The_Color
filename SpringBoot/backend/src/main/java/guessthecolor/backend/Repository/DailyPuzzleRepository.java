package guessthecolor.backend.Repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guessthecolor.backend.Domain.DailyPuzzle;

@Repository
public interface DailyPuzzleRepository extends JpaRepository<DailyPuzzle, Long>{
    
    Optional<DailyPuzzle> findByPuzzleDate(Date puzzleDate);
}
