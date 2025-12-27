package isaeva.userservice.controller;

import isaeva.userservice.jwt.JwtProvider;
import isaeva.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            var u = userService.register(body.get("username"), body.get("password"), body.get("email"), "ROLE_USER");
            return ResponseEntity.ok(Map.of("id", u.getId(), "username", u.getUsername()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (!userService.checkPassword(username, password))
            return ResponseEntity.status(401).body(Map.of("error", "invalid credentials"));
        var opt = userService.findByUsername(username).get();
        String token = jwtProvider.generateToken(username, opt.getRole());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
