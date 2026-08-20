package guessthecolor.backend.Service;

import java.util.Date;

import guessthecolor.backend.Domain.Stats;
import guessthecolor.backend.Domain.User;

public interface StatsService {
    
    Stats updateAfterSession(User user, int score, Date playedDate);
}
