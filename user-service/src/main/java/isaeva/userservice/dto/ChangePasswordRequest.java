package isaeva.userservice.dto;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
