package isaeva.userservice.service;

import isaeva.userservice.dto.AuthResponse;
import isaeva.userservice.dto.JwtResponse;
import isaeva.userservice.dto.UserLoginRequest;
import isaeva.userservice.dto.UserRegistrationRequest;
import isaeva.userservice.exception.UserAlreadyExistsException;
import isaeva.userservice.mapper.UserMapper;
import isaeva.userservice.model.User;
import isaeva.userservice.repository.UserRepository;
import isaeva.userservice.security.JwtAuthenticationFilter;
import isaeva.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(UserRegistrationRequest request) {
       if (userRepository.existsByUsername(request.username())){
           throw new UserAlreadyExistsException("User with username " + request.username() + " already exist");
       }

       User user = userMapper.toEntity(request);
       user.setPassword(passwordEncoder.encode(user.getPassword()));

       userRepository.save(user);

       String token = jwtUtil.generateJwtToken(user.getUsername());
       return userMapper.toAuthResponse(user, token);
    }

    public JwtResponse login(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.usernameOrEmail(),request.password())
        );

        String token = jwtUtil.generateJwtToken(request.usernameOrEmail());
        return new JwtResponse(token, "Bearer", request.usernameOrEmail());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

}
