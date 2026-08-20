package guessthecolor.backend.Service;

import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.PracticeRound;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Domain.User;

public interface PracticeService {

    Practice startSession(User user);
    PracticeRound submitRound(Practice practice, int roundIndex, Color guess);
    Practice completeSession(Practice practice);
}
