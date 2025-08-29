package isaeva.taskservice.dto;

import isaeva.taskservice.enums.TaskStatus;

public record TaskRequest(
        String title,
        String description
) {
}
