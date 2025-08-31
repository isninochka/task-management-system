package isaeva.notificationservice.dto;

public record NotificationResponse(
        String recipient,
        String subject,
        String message,
        String status
) {
}
