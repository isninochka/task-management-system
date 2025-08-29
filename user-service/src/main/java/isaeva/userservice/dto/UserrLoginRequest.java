package isaeva.userservice.dto;

public record UserrLoginRequest(

        String usernameOrEmail,
        String password
) {
}
