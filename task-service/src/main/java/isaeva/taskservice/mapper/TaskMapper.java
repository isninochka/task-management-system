package isaeva.taskservice.mapper;

import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taskStatus", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "histories", ignore = true)
    Task toTask(TaskRequest request);


    @Mapping(target = "taskStatus", expression = "java(task.getTaskStatus() != null ? task.getTaskStatus().name() : null)")
    TaskResponse toTaskResponse(Task task);
}


