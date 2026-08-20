package guessthecolor.backend.Service;

import guessthecolor.backend.Domain.User;

public interface AuthService {
    User login(String username, String password);
}
