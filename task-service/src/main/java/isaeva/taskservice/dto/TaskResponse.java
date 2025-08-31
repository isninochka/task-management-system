package isaeva.taskservice.dto;

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
