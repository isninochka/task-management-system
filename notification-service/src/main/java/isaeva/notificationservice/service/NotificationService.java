package isaeva.notificationservice.service;

import isaeva.notificationservice.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendNotification(NotificationRequest request) {

        log.info("Sending notification to {} with subject '{}' and message '{}'",
                request.recipient(), request.subject(), request.message());
    }
}
