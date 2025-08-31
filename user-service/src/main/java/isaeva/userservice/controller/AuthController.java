package isaeva.userservice.controller;

import isaeva.userservice.dto.AuthResponse;
import isaeva.userservice.dto.ChangePasswordRequest;
import isaeva.userservice.dto.UserLoginRequest;
import isaeva.userservice.dto.UserRegistrationRequest;
import isaeva.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("register")
    public AuthResponse register(@RequestBody UserRegistrationRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginRequest request) {
        return userService.authenticateUser(request);
    }

    @PatchMapping("/{id}/password")
    public void changePassword(@PathVariable Long id,
                               @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id,request.oldPassword(),request.newPassword());
    }

}
