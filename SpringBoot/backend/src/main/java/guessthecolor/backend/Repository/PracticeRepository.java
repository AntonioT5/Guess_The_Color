package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guessthecolor.backend.Domain.Practice;

public interface PracticeRepository extends JpaRepository<Practice, Long>{
    
}
