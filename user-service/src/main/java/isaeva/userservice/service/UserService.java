package isaeva.userservice.service;

import isaeva.userservice.exception.UserAlreadyExistsException;
import isaeva.userservice.exception.UserNotFoundException;
import isaeva.userservice.model.User;
import isaeva.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public User register(String username, String rawPassword, String email, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(rawPassword));
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }


    @Transactional
    public void changePassword(Long userId, String rawPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!encoder.matches(rawPassword, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
        }
        userRepository.save(user);

    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!encoder.matches(password, user.getPassword())) {
            user.setPassword(null);
        }
        return user;
    }

    public boolean checkPassword(String username, String rawPassword) {
        return userRepository.findByUsername(username).map(user -> encoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }


}