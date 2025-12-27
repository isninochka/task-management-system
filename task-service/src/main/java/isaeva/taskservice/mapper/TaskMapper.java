package isaeva.taskservice.mapper;

import isaeva.dtolib.dto.TaskDto;
import isaeva.taskservice.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto dto);
}


