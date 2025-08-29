package isaeva.taskservice.dto;

import isaeva.taskservice.enums.Status;

public record TaskRequest(
        String title,
        String description,
        Status status
) {
}
