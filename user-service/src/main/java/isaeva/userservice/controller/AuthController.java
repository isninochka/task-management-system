package isaeva.userservice.controller;

import isaeva.userservice.dto.JwtResponse;
import isaeva.userservice.dto.UserLoginRequest;
import isaeva.userservice.dto.UserRegistrationRequest;
import isaeva.userservice.model.User;
import isaeva.userservice.security.JwtUtil;
import isaeva.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("register")
    public JwtResponse register (@RequestBody UserRegistrationRequest request) {
        User user = userService.register(request);
        String token = jwtUtil.generateJwtToken(user.getUsername());
        return new JwtResponse(token, user.getUsername());
    }

    @PostMapping("/login")
    public JwtResponse login (@RequestBody UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.usernameOrEmail(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateJwtToken(authentication.getName());

        return new JwtResponse(token, authentication.getName());
    }

}
