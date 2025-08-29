package isaeva.userservice.dto;

public record UserLoginRequest(

        String usernameOrEmail,
        String password
) {
}
