package isaeva.userservice.controller;

import isaeva.userservice.dto.ChangePasswordRequest;
import isaeva.userservice.dto.UserCreateRequest;
import isaeva.userservice.dto.UserResponse;
import isaeva.userservice.model.User;
import isaeva.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@RequestBody @Valid UserCreateRequest request) {
        User user = userService.createUser(request);
        return new UserResponse(user.getId(), user.getUsername());
    }

    @PatchMapping("/{id}/password")
    public void changePassword(@PathVariable Long id,
                               @RequestBody @Valid ChangePasswordRequest request) {
        log.info("Changing password for user {}", id);
        userService.changePassword(id, request);
    }
}