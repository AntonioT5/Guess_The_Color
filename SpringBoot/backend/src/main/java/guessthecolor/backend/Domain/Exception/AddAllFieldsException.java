package guessthecolor.backend.Domain.Exception;

public class AddAllFieldsException extends RuntimeException {
    public AddAllFieldsException() {
        super("Fill all the fileds");
    }
}
