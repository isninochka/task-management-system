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

        Task task = taskMapper.toEntity(request);
        task.setUsername(username);
        task.setTaskStatus(TaskStatus.NEW);
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);

        saveTaskHistory(task.getId(), username, null, TaskStatus.NEW);
        return taskMapper.toDto(task);
    }

    public List<TaskResponse> getTasksByUser(String username) {
        return taskRepository.findByUsernameAndTaskStatusNot(username, TaskStatus.DELETED)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public TaskResponse startTask(Long id, String username) {
        Task task = findOwnedTask(id, username);

        if (task.getTaskStatus() == TaskStatus.DELETED) {
            throw new BadRequestException("Task is deleted");
        }

        if (task.getTaskStatus() == TaskStatus.COMPLETED) {
            throw new BadRequestException("Can't start a completed task");
        }

        if (task.getTaskStatus() == TaskStatus.IN_PROGRESS) {
            return taskMapper.toDto(task);
        }

        TaskStatus old = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);

        saveTaskHistory(task.getId(), username, old, TaskStatus.IN_PROGRESS);
        return taskMapper.toDto(task);
    }

    public TaskResponse completeTask(Long id, String username) {
        Task task = findOwnedTask(id, username);

        if (task.getTaskStatus() != TaskStatus.IN_PROGRESS) {
            throw new BadRequestException("Task must be IN_PROGRESS to be complete");
        }

        TaskStatus old = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);

        saveTaskHistory(task.getId(), username, old, TaskStatus.COMPLETED);
        return taskMapper.toDto(task);
    }

    public void deleteTask(Long id, String username) {

        Task task = findOwnedTask(id, username);
        if (task.getTaskStatus() == TaskStatus.DELETED) {
            throw new BadRequestException("Task was already deleted");
        }

        TaskStatus old = task.getTaskStatus();
        task.setTaskStatus(TaskStatus.DELETED);
        taskRepository.save(task);

        saveTaskHistory(task.getId(), username, old, TaskStatus.DELETED);
    }

    public List<TaskHistoryDto> getTaskHistory(Long taskId, String username) {
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

    private void saveTaskHistory(Long taskId, String username, TaskStatus oldStatus, TaskStatus newStatus) {

        TaskHistory taskHistory = TaskHistory.builder()
                .taskId(taskId)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .username(username)
                .changedAt(LocalDateTime.now())
                .build();
    }


}
