// package guessthecolor.backend.Service.Impl;

// import org.springframework.stereotype.Service;

// import guessthecolor.backend.Domain.Exception.InvalidArgumentsException;
// import guessthecolor.backend.Domain.Exception.InvalidUserCredentialsException;
// import guessthecolor.backend.Domain.User;
// import guessthecolor.backend.Repository.UserRepository;
// import guessthecolor.backend.Service.AuthService;

// @Service
// public class AuthServiceImpl implements AuthService {

//     private final UserRepository userRepository;

//     public AuthServiceImpl(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     @Override
//     public User login(String mail, String password) {
//         if (mail == null || mail.isEmpty() || password == null || password.isEmpty()) {
//             throw new InvalidArgumentsException();
//         }

//         return this.userRepository.findByMailAndPassword(mail, password)
//                                   .orElseThrow(InvalidUserCredentialsException::new);
//     }
    
// }
