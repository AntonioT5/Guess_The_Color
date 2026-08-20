package guessthecolor.backend.Service.Impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import guessthecolor.backend.Domain.Stats;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Repository.StatsRepository;
import guessthecolor.backend.Service.StatsService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public Stats updateAfterSession(User user, int score, Date playedDate) {
        Stats stats = statsRepository.findByUser(user).orElseGet(()->{
            Stats s = new Stats();
            s.setAvgScore(0);
            s.setBestScore(0);
            s.setUser(user);
            return s;
        });

        if (score>stats.getBestScore()){
            stats.setBestScore(score);
        }
        stats.setAvgScore((stats.getAvgScore()+score)/2);
        stats.setLastPLayedTime(playedDate);
        return statsRepository.save(stats);
    }
    
}
