package guessthecolor.backend.Bootstrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import guessthecolor.backend.Domain.Enums.Role;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DataHolder {
    
    public static List<User> users = null;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataHolder(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init(){
        if (userRepository.findAll().isEmpty()) {
            users = new ArrayList<>();
            users.add(new User("admin", passwordEncoder.encode("admin"), "admin@admin.com", new Date(), Role.ROLE_ADMIN));
            userRepository.saveAll(users);
        }

    }
}
