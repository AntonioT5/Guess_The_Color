package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guessthecolor.backend.Domain.PracticeRound;

@Repository
public interface PracticeRoundRepository extends JpaRepository<PracticeRound, Long>{

}
