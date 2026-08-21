package guessthecolor.backend.Domain.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String mail){
        super(String.format("User with %s mail doesn't exists.", mail));
    }
    
}
