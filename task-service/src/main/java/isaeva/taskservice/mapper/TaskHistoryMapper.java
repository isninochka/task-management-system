package isaeva.taskservice.mapper;

import isaeva.taskservice.dto.TaskHistoryDto;
import isaeva.taskservice.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskHistoryMapper {

    @Mapping(target = "taskId", expression = "java(taskHistory.getTask().getId())")
    @Mapping(source = "oldStatus", target = "previousStatus")
    @Mapping(source = "newStatus", target = "newStatus")
    @Mapping(target = "username", expression = "java(taskHistory.getUsername())")
    TaskHistoryDto toDto(TaskHistory taskHistory);
}
