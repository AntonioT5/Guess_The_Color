package guessthecolor.backend.Service.Impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Repository.DailyPuzzleRepository;
import guessthecolor.backend.Service.DailyPuzzleService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DailyPuzzleServiceImpl implements DailyPuzzleService{

    private final DailyPuzzleRepository dailyPuzzleRepository;

    @Override
    public DailyPuzzle getOrCreateTodaysPuzzle() {
        Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        return dailyPuzzleRepository.findByPuzzleDate(today).orElseGet(()->{
            long seed = LocalDate.now().toEpochDay();
            DailyPuzzle puzzle = new DailyPuzzle();
            puzzle.setPuzzleDate(today);
            puzzle.setSeed(seed);
            return dailyPuzzleRepository.save(puzzle);
        });
    }
    
}
