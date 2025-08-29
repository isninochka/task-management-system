package isaeva.taskservice.dto;

import isaeva.taskservice.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String taskStatus,
        String username,
        LocalDateTime createdAt
) {
}
