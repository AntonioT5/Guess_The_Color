package guessthecolor.backend.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.User;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Long>{
    Page<Practice> findByUserAndComplitedTrueOrderByCreatedAtDesc(User user, Pageable pageable);
    List<Practice> findByUserOrderByCreatedAtDesc(User user);
}
