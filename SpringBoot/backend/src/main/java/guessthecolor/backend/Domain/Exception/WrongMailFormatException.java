package guessthecolor.backend.Domain.Exception;

public class WrongMailFormatException extends RuntimeException {
    public WrongMailFormatException() {
        super("Input real email");
    }
}
