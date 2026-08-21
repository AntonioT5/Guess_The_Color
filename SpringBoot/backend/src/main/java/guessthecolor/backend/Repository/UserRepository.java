package guessthecolor.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guessthecolor.backend.Domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByMail(String mail);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
