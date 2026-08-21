package guessthecolor.backend.Service.Impl;

import java.util.Date;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import guessthecolor.backend.Domain.Enums.Role;
import guessthecolor.backend.Domain.Exception.PasswordsDoNotMatchException;
import guessthecolor.backend.Domain.Exception.UserAlreadyExistsException;
import guessthecolor.backend.Domain.Exception.UserNotFoundException;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Repository.UserRepository;
import guessthecolor.backend.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String mail, Date date, Role role) throws InvalidArgumentsException {

        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }

        if(!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }

        if(userRepository.findByMail(mail).isPresent()){
            throw new UserAlreadyExistsException(mail);
        }

        User user = new User(username, passwordEncoder.encode(password), mail, date, role);

        return userRepository.save(user);
    }

    @Override
    public User findByMail(String mail) throws UserNotFoundException {
        return userRepository.findByMail(mail).orElseThrow(() -> new UserNotFoundException(mail));
    }

    @Override
    public UserDetails loadUserByMail(String mail) {
        return userRepository.findByMail(mail)
                .orElseThrow(() -> new BadCredentialsException("Invalid mail or password!"));
    }

    @Override
    public User editData(String oldUsername, String username, String mail) {
        User user = userRepository.findByUsername(oldUsername).orElseThrow(()->new UserNotFoundException(mail));

        user.setUsername(username);
        if(userRepository.findByMail(mail) != null){
            throw new UserAlreadyExistsException(mail);
        }

        user.setMail(mail);

        return userRepository.save(user);
    }
    
}
