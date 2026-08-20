package guessthecolor.backend.Service;

import java.util.List;

import guessthecolor.backend.Domain.DailyAttemptRound;
import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Domain.DailyPuzzleAttempt;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Domain.User;

public interface DailyPuzzleAttemptService {
    
    DailyPuzzleAttempt startAttempt(User user, DailyPuzzle puzzle);
    DailyAttemptRound submitRound(DailyPuzzleAttempt attempt, int roundIndex, Color guess);
    DailyPuzzleAttempt completeAttempt(DailyPuzzleAttempt attempt);
    
    List<DailyPuzzleAttempt> getLeaderboard(DailyPuzzle puzzle);
}
