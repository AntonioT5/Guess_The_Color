package guessthecolor.backend.Service;

import guessthecolor.backend.Domain.DailyPuzzle;

public interface DailyPuzzleService {
    
    DailyPuzzle getOrCreateTodaysPuzzle();
}
