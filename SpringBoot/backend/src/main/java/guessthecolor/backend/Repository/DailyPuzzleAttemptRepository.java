package guessthecolor.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Domain.DailyPuzzleAttempt;
import guessthecolor.backend.Domain.User;

@Repository
public interface DailyPuzzleAttemptRepository extends JpaRepository<DailyPuzzleAttempt, Long>{
    boolean existsByUserAndDailyPuzzle(User user, DailyPuzzle puzzle);
    Optional<DailyPuzzleAttempt> findByUserAndDailyPuzzle(User user, DailyPuzzle puzzle);
    List<DailyPuzzleAttempt> findByDailyPuzzleOrderByTotalScoreDesc(DailyPuzzle puzzle);
}
