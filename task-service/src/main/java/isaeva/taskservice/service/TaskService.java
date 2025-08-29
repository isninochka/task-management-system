package isaeva.taskservice.service;

import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.enums.TaskStatus;
import isaeva.taskservice.exception.BadRequestException;
import isaeva.taskservice.exception.ForbiddenException;
import isaeva.taskservice.exception.NotFoundException;
import isaeva.taskservice.model.Task;
import isaeva.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskRequest request, String username) {

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .taskStatus(TaskStatus.NEW)
                .username(username)
                .createdAt(LocalDateTime.now())
                .build();


        return map(taskRepository.save(task));
    }

    public List<TaskResponse> getTasksByUser (String username) {
        return taskRepository.findByUsernameAndTaskStatusNot(username, TaskStatus.DELETED)
                .stream()
                .map( this::map)
                .toList();
    }

    public TaskResponse startTask (Long id,String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        checkOwnership(task,username);

        if (task.getTaskStatus() == TaskStatus.DELETED) {
            throw new BadRequestException("Task is deleted");
        }

        if (task.getTaskStatus() == TaskStatus.COMPLETED) {
            throw new BadRequestException("Can't start a completed task");
        }

        if (task.getTaskStatus() == TaskStatus.IN_PROGRESS) {
            return map(task);
        }

        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        return map(taskRepository.save(task));
    }

    public TaskResponse completeTask (Long id,String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        checkOwnership(task,username);
        if (task.getTaskStatus() == TaskStatus.DELETED) {
            throw new BadRequestException("Task is deleted");
        }

        if (task.getTaskStatus() == TaskStatus.COMPLETED) {
            return map(task);
        }

        if (task.getTaskStatus() == TaskStatus.IN_PROGRESS) {
            throw new BadRequestException("Task must be IN_PROGRESS to be completed");
        }

        task.setTaskStatus(TaskStatus.COMPLETED);
        return map(taskRepository.save(task));
    }

    public void deleteTask (Long id, String username) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        checkOwnership(task,username);
        if (task.getTaskStatus() == TaskStatus.DELETED) {
            throw new BadRequestException("Task is deleted");
        }

        task.setTaskStatus(TaskStatus.DELETED);
        taskRepository.save(task);
    }
    private void checkOwnership(Task task, String username) {
        if (!task.getUsername().equals(username)) {
            throw new ForbiddenException("Not allowed to modify this task");
        }
    }
    private TaskResponse map(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus().name(),
                task.getUsername(),
                task.getCreatedAt()
        );
    }

}
