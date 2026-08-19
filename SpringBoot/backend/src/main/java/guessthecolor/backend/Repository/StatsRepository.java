package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guessthecolor.backend.Domain.Stats;

public interface StatsRepository extends JpaRepository<Stats, Long>{
    
}
