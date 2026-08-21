package guessthecolor.backend.Domain.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String mail) {
        super(String.format("This %s email is already taken", mail));
    }
}
