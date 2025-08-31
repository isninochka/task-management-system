package isaeva.notificationservice.dto;

public record NotificationRequest(
        String recipient,
        String subject,
        String message
) {
}
