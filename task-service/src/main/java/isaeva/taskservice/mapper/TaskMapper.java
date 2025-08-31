package isaeva.taskservice.mapper;

import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toTask(TaskRequest request);


    @Mapping(target = "username", ignore = true)
    @Mapping(target = "taskStatus", expression = "java(task.getTaskStatus() != null ? task.getTaskStatus().name() : null)")
    TaskResponse toTaskResponse(Task task);


}
