package guessthecolor.backend.Service.Impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.PracticeRound;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Repository.PracticeRepository;
import guessthecolor.backend.Repository.PracticeRoundRepository;
import guessthecolor.backend.Service.ColorService;
import guessthecolor.backend.Service.PracticeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PracticeServiceImpl implements PracticeService {

    private final PracticeRepository practiceRepository;
    private final PracticeRoundRepository roundRepository;
    private final ColorService colorService;

    @Override
    public Practice startSession(User user) {
        Practice practice = new Practice();
        practice.setUser(user);
        practice.setSeed(new java.util.Random().nextLong());
        practice.setTotalScore(0);
        practice.setCreatedAt(new Date());
        return practiceRepository.save(practice);
    }

    @Override
    public PracticeRound submitRound(Practice practice, int roundIndex, Color guess) {
        long seed = practice.getSeed();
        Color target = colorService.generateColors(seed).get(roundIndex);

        double deltaE = colorService.calculateDeltaE(target, guess);
        double score = colorService.calculateScore(deltaE);

        PracticeRound round = new PracticeRound();
        practice.getPracticeRounds().add(round);
        round.setPractice(practice);
        round.setRoundIndex(roundIndex);
        round.setTargetR(target.r());
        round.setTargetG(target.g());
        round.setTargetB(target.b());
        round.setGuessR(guess.r());
        round.setGuessG(guess.g());
        round.setGuessB(guess.b());
        round.setDeltaE(deltaE);
        round.setScore(score);

        return roundRepository.save(round);
    }

    @Override
    public Practice completeSession(Practice practice) {
        double total = practice.getPracticeRounds().stream().mapToInt(r-> Math.round((float) r.getScore())).sum();
        practice.setTotalScore(total);
        return practiceRepository.save(practice);
    }
    
}
