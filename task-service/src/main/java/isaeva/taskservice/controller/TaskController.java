package isaeva.taskservice.controller;

import isaeva.dtolib.dto.TaskDto;
import isaeva.taskservice.entity.Task;
import isaeva.taskservice.mapper.TaskMapper;
import isaeva.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getAllTasks() {

        log.info("All tasks found");

        return taskService.allTasks().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto) {

        Task task = taskService.create(taskMapper.toEntity(taskDto));

        log.info("Created task: {}", task);

        return taskMapper.toDto(task);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Task task = taskService.update(id, taskMapper.toEntity(taskDto));
        log.info("Updated task: {}", task);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void cancelTask(@PathVariable Long id) {
        taskService.cancelled(id);
    }


}