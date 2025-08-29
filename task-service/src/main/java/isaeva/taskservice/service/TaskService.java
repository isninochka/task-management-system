package isaeva.taskservice.service;

import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.enums.Status;
import isaeva.taskservice.model.Task;
import isaeva.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskRequest request, String username) {

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(Status.NEW)
                .username(username)
                .createdAt(LocalDateTime.now())
                .build();
        taskRepository.save(task);

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUsername(),
                task.getCreatedAt()
        );
    }

    public List<TaskResponse> getTasksByUser (String username) {
        return taskRepository.findByUsername(username)
                .stream()
                .map( task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getUsername(),
                        task.getCreatedAt()
                ))
                .toList();
    }

    public void deleteTask (Long id, String username) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUsername().equals(username)) {
            throw new RuntimeException("Not allowed to delete task");
        }

        taskRepository.delete(task);
    }

}
