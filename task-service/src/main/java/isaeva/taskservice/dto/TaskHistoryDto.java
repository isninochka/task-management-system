package isaeva.taskservice.dto;

import java.time.LocalDateTime;

public record TaskHistoryDto(
        Long id,
        Long taskId,
        String previousStatus,
        String newStatus,
        Long changedBy,
        LocalDateTime changedAt) {
}
