package isaeva.notificationservice.controller;

import isaeva.notificationservice.dto.NotificationRequest;
import isaeva.notificationservice.dto.NotificationResponse;
import isaeva.notificationservice.mapper.NotificationMapper;
import isaeva.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody NotificationRequest notificationRequest) {

        notificationService.sendNotification(notificationRequest);
        return ResponseEntity.ok(notificationMapper.toResponse(notificationRequest));
    }
}
