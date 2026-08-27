package guessthecolor.backend.Service;

import org.springframework.data.domain.Page;

import guessthecolor.backend.Domain.DailyAttemptRound;
import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Domain.DailyPuzzleAttempt;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Domain.User;

public interface DailyPuzzleAttemptService {
    
    DailyPuzzleAttempt startAttempt(User user, DailyPuzzle puzzle);
    DailyAttemptRound submitRound(DailyPuzzleAttempt attempt, int roundIndex, Color guess);
    DailyPuzzleAttempt completeAttempt(DailyPuzzleAttempt attempt);
    boolean userAndPuzzleExists(User user, DailyPuzzle puzzle);
    DailyPuzzleAttempt findByUserAndPuzzle(User user, DailyPuzzle puzzle);
    
    Page<DailyPuzzleAttempt> getLeaderboard(DailyPuzzle puzzle, int page, int size);
}
