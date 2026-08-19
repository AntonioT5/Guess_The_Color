package guessthecolor.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guessthecolor.backend.Domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
