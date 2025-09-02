package isaeva.taskservice.controller;

import isaeva.taskservice.dto.TaskHistoryDto;
import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@RequestBody @Valid TaskRequest request,
                                   @RequestHeader("X-Username") String username) {
        log.info("User {} is creating a task", username);
        return taskService.createTask(request, username);
    }

    @GetMapping
    public List<TaskResponse> getMyTasks(@RequestHeader("X-Username") String username) {
        log.info("User {} is fetching tasks", username);
        return taskService.getTasksByUser(username);
    }

    @PostMapping("/{id}/start")
    public TaskResponse startTask(@PathVariable Long id,
                                  @RequestHeader("X-Username") String username) {
        log.info("User {} is starting task {}", username, id);
        return taskService.startTask(id, username);
    }

    @PostMapping("/{id}/complete")
    public TaskResponse completeTask(@PathVariable Long id,
                                     @RequestHeader("X-Username") String username) {
        log.info("User {} is completing task {}", username, id);
        return taskService.completeTask(id, username);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id,
                           @RequestHeader("X-Username") String username) {
        log.info("User {} is deleting task {}", username, id);
        taskService.deleteTask(id, username);
    }

    @GetMapping("/{taskId}/history")
    public List<TaskHistoryDto> getHistory(@PathVariable Long taskId,
                                           @RequestHeader("X-Username") String username) {
        log.info("User {} is fetching history for task {}", username, taskId);
        return taskService.getTaskHistory(taskId, username);
    }
}