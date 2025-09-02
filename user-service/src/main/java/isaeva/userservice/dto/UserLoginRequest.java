package isaeva.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(

        @NotBlank @Size(min = 2, max = 16)
        String username,
        @NotBlank @Size(min = 8)
        String password
) {
}
