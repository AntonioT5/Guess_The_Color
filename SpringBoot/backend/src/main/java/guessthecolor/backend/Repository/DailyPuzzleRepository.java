package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guessthecolor.backend.Domain.DailyPuzzle;

public interface DailyPuzzleRepository extends JpaRepository<DailyPuzzle, Long>{
    
}
