package guessthecolor.backend.Service;

import java.util.Optional;

import guessthecolor.backend.Domain.User;

public interface UserService {
    User register(String username, String password, String repeatPassword, String surname);
    Optional<User> findByUsername(String username);
}
