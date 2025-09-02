package isaeva.taskservice.service;

import isaeva.taskservice.dto.TaskHistoryDto;
import isaeva.taskservice.dto.TaskRequest;
import isaeva.taskservice.dto.TaskResponse;
import isaeva.taskservice.enums.TaskStatus;
import isaeva.taskservice.exception.BadRequestException;
import isaeva.taskservice.exception.ForbiddenException;
import isaeva.taskservice.exception.TaskNotFoundException;
import isaeva.taskservice.mapper.TaskHistoryMapper;
import isaeva.taskservice.mapper.TaskMapper;
import isaeva.taskservice.model.Task;
import isaeva.taskservice.model.TaskHistory;
import isaeva.taskservice.repository.TaskHistoryRepository;
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
    private final TaskMapper taskMapper;
    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskHistoryMapper taskHistoryMapper;

    public TaskResponse createTask(TaskRequest request, String username) {
        validateUsername(username);

        Task task = taskMapper.toTask(request);
        task.setUsername(username);
        task.setTaskStatus(TaskStatus.NEW);
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);

        saveTaskHistory(task, null, TaskStatus.NEW, username);
        return buildTaskResponse(task);
    }

    public List<TaskResponse> getTasksByUser(String username) {
        validateUsername(username);

        return taskRepository.findByUsernameAndTaskStatusNot(username, TaskStatus.DELETED)
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    public TaskResponse startTask(Long id, String username) {
        validateUsername(username);

        Task task = findOwnedTask(id, username);

        switch (task.getTaskStatus()) {
            case DELETED -> throw new BadRequestException("Task is deleted");
            case COMPLETED -> throw new BadRequestException("Can't start a completed task");
            case IN_PROGRESS -> { return buildTaskResponse(task); }
        }

        TaskStatus old = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.IN_PROGRESS);

        saveTaskHistory(task, old, TaskStatus.IN_PROGRESS, username);
        return buildTaskResponse(task);
    }

    public TaskResponse completeTask(Long id, String username) {
        validateUsername(username);

        Task task = findOwnedTask(id, username);

        if (task.getTaskStatus() != TaskStatus.IN_PROGRESS) {
            throw new BadRequestException("Task must be IN_PROGRESS to complete");
        }

        TaskStatus old = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.COMPLETED);

        saveTaskHistory(task, old, TaskStatus.COMPLETED, username);
        return buildTaskResponse(task);
    }

    public void deleteTask(Long id, String username) {
        validateUsername(username);

        Task task = findOwnedTask(id, username);
        if (task.getTaskStatus() == TaskStatus.DELETED) {
            throw new BadRequestException("Task was already deleted");
        }

        TaskStatus old = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.DELETED);

        saveTaskHistory(task, old, TaskStatus.DELETED, username);
    }

    public List<TaskHistoryDto> getTaskHistory(Long taskId, String username) {
        validateUsername(username);

        taskRepository.findById(taskId)
                .filter(task -> task.getUsername().equals(username))
                .orElseThrow(() -> new ForbiddenException("Not allowed"));

        return taskHistoryRepository.findByTaskId(taskId)
                .stream()
                .map(taskHistoryMapper::toDto)
                .toList();
    }

    private Task findOwnedTask(Long id, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        if (!task.getUsername().equals(username)) {
            throw new ForbiddenException("Not allowed to modify this task");
        }
        return task;
    }

    private void saveTaskHistory(Task task, TaskStatus oldStatus, TaskStatus newStatus, String username) {
        TaskHistory taskHistory = TaskHistory.builder()
                .task(task)
                .previousStatus(oldStatus)
                .newStatus(newStatus)
                .username(username)
                .changedAt(LocalDateTime.now())
                .build();
        taskHistoryRepository.save(taskHistory);
    }

    private TaskResponse buildTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus() != null ? task.getTaskStatus().name() : null,
                task.getUsername(),
                task.getCreatedAt()
        );
    }

    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username is required");
        }
    }
}