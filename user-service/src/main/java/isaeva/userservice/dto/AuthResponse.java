package isaeva.userservice.dto;

public record AuthResponse(

        String token,
        String username,
        String email
) {
}
