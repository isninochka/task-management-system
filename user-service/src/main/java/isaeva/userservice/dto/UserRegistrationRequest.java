package isaeva.userservice.dto;

public record UserRegistrationRequest (
        String username,
        String email,
        String password
){
}
