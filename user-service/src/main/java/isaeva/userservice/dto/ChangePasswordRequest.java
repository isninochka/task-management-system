package isaeva.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank @Size(min = 8)
        String oldPassword,
        @NotBlank @Size(min = 8)
        String newPassword
) {
}
