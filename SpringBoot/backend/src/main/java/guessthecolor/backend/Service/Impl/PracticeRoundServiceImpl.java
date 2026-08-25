package guessthecolor.backend.Service.Impl;

import org.springframework.stereotype.Service;

import guessthecolor.backend.Domain.PracticeRound;
import guessthecolor.backend.Repository.PracticeRoundRepository;
import guessthecolor.backend.Service.PracticeRoundService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PracticeRoundServiceImpl implements PracticeRoundService {
    
    private final PracticeRoundRepository repository;

    public PracticeRound findById(Long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid round Id: " + id));
    }
}
