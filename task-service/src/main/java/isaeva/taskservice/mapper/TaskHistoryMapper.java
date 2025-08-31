package isaeva.taskservice.mapper;

import isaeva.taskservice.dto.TaskHistoryDto;
import isaeva.taskservice.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskHistoryMapper {

    @Mapping(target = "previousStatus", expression = "java(taskHistory.getPreviousStatus() != " +
            "null ? taskHistory.getPreviousStatus().name() : null)")
    @Mapping(target = "newStatus", expression = "java(taskHistory.getNewStatus() != " +
            "null ? taskHistory.getNewStatus().name() : null)")
    TaskHistoryDto toDto(TaskHistory taskHistory);
}
