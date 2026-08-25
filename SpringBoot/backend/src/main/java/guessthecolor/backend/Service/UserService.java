package guessthecolor.backend.Service;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import guessthecolor.backend.Domain.Exception.InvalidArgumentsException;
import guessthecolor.backend.Domain.User;

public interface UserService {
    User register(String username, String password, String repeatPassword,  String mail, Date date) throws InvalidArgumentsException;
    User findByMail(String mail);

    public UserDetails loadUserByMail(String mail);

    User editData(String oldUsername, String oldMail, String username, String mail);
}
