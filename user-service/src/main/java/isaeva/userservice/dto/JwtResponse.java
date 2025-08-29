package isaeva.userservice.dto;

public record JwtResponse(
        String accessToken,
        String tokenType,
        String username
) {
    public JwtResponse(String accessToken, String username) {
        this(accessToken, "Bearer", username);
    }
}
