package isaeva.taskservice.controller;

import isaeva.taskservice.dto.TaskHistoryDto;
import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Transactional
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request,
                                                   @AuthenticationPrincipal User user) {
        TaskResponse created = taskService.createTask(request, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request, user.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getMyTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getTasksByUser(user.getUsername()));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<TaskResponse> startTask(@PathVariable Long id,
                                                  @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.startTask(id, user.getUsername()));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id,
                                                     @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.completeTask(id, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,
                                           @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}/history")
    public ResponseEntity<List<TaskHistoryDto>> getHistory(@PathVariable Long taskId,
                                                           @AuthenticationPrincipal User user) {
        return ResponseEntity.ok((taskService.getTaskHistory(taskId, user.getUsername())));
    }
}
