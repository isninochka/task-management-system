package isaeva.taskservice.dto;

import isaeva.taskservice.enums.Status;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Status status,
        String username,
        LocalDateTime createdAt
) {
}
