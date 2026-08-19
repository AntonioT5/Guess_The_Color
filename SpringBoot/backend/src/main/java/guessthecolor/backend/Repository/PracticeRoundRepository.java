package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guessthecolor.backend.Domain.PracticeRound;

public interface PracticeRoundRepository extends JpaRepository<PracticeRound, Long>{
    
}
