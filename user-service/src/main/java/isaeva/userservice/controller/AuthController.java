package isaeva.userservice.controller;

import isaeva.userservice.dto.AuthResponse;
import isaeva.userservice.dto.JwtResponse;
import isaeva.userservice.dto.UserLoginRequest;
import isaeva.userservice.dto.UserRegistrationRequest;
import isaeva.userservice.model.User;
import isaeva.userservice.security.JwtUtil;
import isaeva.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthResponse> register (@RequestBody UserRegistrationRequest request) {
           return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@RequestBody UserLoginRequest request) {
      return ResponseEntity.ok(userService.login(request));
    }

}
