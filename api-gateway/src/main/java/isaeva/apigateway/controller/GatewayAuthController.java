package isaeva.apigateway.controller;

import isaeva.apigateway.dto.AuthResponse;
import isaeva.apigateway.dto.UserRegistrationRequest;
import isaeva.apigateway.jwt.JwtUtil;
import isaeva.apigateway.service.UserServiceClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class GatewayAuthController {

    private final UserServiceClient userServiceClient;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid UserRegistrationRequest request) {
        var user = userServiceClient.createUser(request);
        String token = jwtUtil.generateJwtToken(user.username());
        log.info("Registered user {}", user.username());
        return new AuthResponse(token);
    }
}
