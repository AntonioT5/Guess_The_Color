package guessthecolor.backend.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.PracticeRound;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Domain.User;

public interface PracticeRoundService {

    PracticeRound findById(Long id);
}
