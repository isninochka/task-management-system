package isaeva.userservice.controller;

import isaeva.userservice.dto.AuthResponse;
import isaeva.userservice.dto.ChangePasswordRequest;
import isaeva.userservice.dto.UserLoginRequest;
import isaeva.userservice.dto.UserRegistrationRequest;
import isaeva.userservice.jwt.JwtUtil;
import isaeva.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserRegistrationRequest request) {

        var user = userService.register(request);
        String token = jwtUtil.generateJwtToken(user.token());
        log.info("Registering user {}", request);
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginRequest request) {

        var user = userService.authenticateUser(request);
        String token = jwtUtil.generateJwtToken(user.token());
        log.info("Logining user {}", request);
        return new AuthResponse(token);
    }

    @PatchMapping("/{id}/password")
    public void changePassword(@PathVariable Long id,
                               @RequestBody ChangePasswordRequest request) {

        log.info("Changing password for user {}", id);
        userService.changePassword(id,request.oldPassword(),request.newPassword());
    }

}
