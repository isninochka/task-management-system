package isaeva.taskservice.controller;

import isaeva.taskservice.dto.TaskHistoryDto;
import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request,
                                   @RequestHeader("X-Username") String username) {
        return taskService.createTask(request, username);
    }

    @GetMapping
    public List<TaskResponse> getMyTasks(@RequestHeader("X-Username") String username) {
        return taskService.getTasksByUser(username);
    }

    @PostMapping("/{id}/start")
    public TaskResponse startTask(@PathVariable Long id,
                                  @RequestHeader("X-Username") String username) {
        return taskService.startTask(id, username);
    }

    @PostMapping("/{id}/complete")
    public TaskResponse completeTask(@PathVariable Long id,
                                     @RequestHeader("X-Username") String username) {
        return taskService.completeTask(id, username);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id,
                           @RequestHeader("X-Username") String username) {
        taskService.deleteTask(id, username);
    }

    @GetMapping("/{taskId}/history")
    public List<TaskHistoryDto> getHistory(@PathVariable Long taskId,
                                           @RequestHeader("X-Username") String username) {
        return taskService.getTaskHistory(taskId, username);
    }
}
