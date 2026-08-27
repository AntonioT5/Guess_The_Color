package guessthecolor.backend.Service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import guessthecolor.backend.Domain.DailyAttemptRound;
import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Domain.DailyPuzzleAttempt;
import guessthecolor.backend.Domain.Exception.UsernameNotFoundException;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Repository.DailyAttemptRoundRepository;
import guessthecolor.backend.Repository.DailyPuzzleAttemptRepository;
import guessthecolor.backend.Service.ColorService;
import guessthecolor.backend.Service.DailyPuzzleAttemptService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DailyPuzzleAttemptServiceImpl implements DailyPuzzleAttemptService{
    
    private final DailyPuzzleAttemptRepository attemptRepository;
    private final DailyAttemptRoundRepository roundRepository;
    private final ColorService colorService;

    @Override
    public DailyPuzzleAttempt startAttempt(User user, DailyPuzzle puzzle) {
        if (attemptRepository.existsByUserAndDailyPuzzle(user, puzzle)){
            throw new IllegalStateException("User has already played today's game;");
        }
        DailyPuzzleAttempt attempt = new DailyPuzzleAttempt();
        attempt.setUser(user);
        attempt.setDailyPuzzle(puzzle);
        attempt.setTotalScore(0);
        return attemptRepository.save(attempt);
    }

    @Override
    public DailyAttemptRound submitRound(DailyPuzzleAttempt attempt, int roundIndex, Color guess) {
        long seed = attempt.getDailyPuzzle().getSeed();
        Color target = colorService.generateColors(seed).get(roundIndex);

        double deltaE = colorService.calculateDeltaE(target, guess);
        double score = colorService.calculateScore(deltaE);

        DailyAttemptRound round = new DailyAttemptRound();
        attempt.getDailyAttemptRounds().add(round); 
        round.setDailyPuzzleAttempt(attempt);
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
    public DailyPuzzleAttempt completeAttempt(DailyPuzzleAttempt attempt) {
        List<DailyAttemptRound> rounds = attempt.getDailyAttemptRounds();
        double total = rounds.stream().mapToInt(r-> Math.round((float) r.getScore())).sum();

        attempt.setTotalScore(total);
        attempt.setCompletedAt(new Date());
        return attemptRepository.save(attempt);
    }

    @Override
    public Page<DailyPuzzleAttempt> getLeaderboard(DailyPuzzle puzzle, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attemptRepository.findByDailyPuzzleOrderByTotalScoreDesc(puzzle, pageable);
    }

    @Override
    public boolean userAndPuzzleExists(User user, DailyPuzzle puzzle) {
        return attemptRepository.existsByUserAndDailyPuzzle(user, puzzle);
    }

    @Override
    public DailyPuzzleAttempt findByUserAndPuzzle(User user, DailyPuzzle puzzle) {
        return attemptRepository.findByUserAndDailyPuzzle(user, puzzle).orElseThrow(()->new UsernameNotFoundException(user.getUsername()));
    }
    
}
