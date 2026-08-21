package guessthecolor.backend.Service;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import guessthecolor.backend.Domain.Enums.Role;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Service.Impl.InvalidArgumentsException;

public interface UserService {
    User register(String username, String password, String repeatPassword,  String mail, Date date, Role role) throws InvalidArgumentsException;
    User findByMail(String mail);

    public UserDetails loadUserByMail(String mail);

    User editData(String oldUsername, String username, String mail);
}
