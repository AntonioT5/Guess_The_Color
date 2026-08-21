package guessthecolor.backend.Domain.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String mail) {
        super("User with this email is already taken");
    }
}
