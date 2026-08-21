package guessthecolor.backend.Domain.Exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String username){
        super(String.format("User with %s username doesn't exists.", username));
    }
}
