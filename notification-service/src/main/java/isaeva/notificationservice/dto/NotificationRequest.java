package isaeva.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(
        @NotBlank
        String recipient,
        @NotBlank
        String subject,
        @NotBlank
        String message
) {
}
