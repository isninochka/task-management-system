package isaeva.dtolib.dto;

public record TaskDto (
        Long id,
        String title,
        String description,
        String status,
        Long assigneeId
) {
}
