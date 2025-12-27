package isaeva.taskservice.service;

import isaeva.taskservice.entity.Task;
import isaeva.taskservice.enums.TaskStatus;
import isaeva.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> allTasks() {
        log.info("Get all tasks");
        return taskRepository.findAll();
    }

    public Task create(Task task) {
        if (task.getTaskStatus() == null)
            task.setTaskStatus(TaskStatus.NEW);
        log.info("Create new task: " + task);
        return taskRepository.save(task);
    }

    public Task update(Long id, Task task) {
        var t = taskRepository.findById(id);
        if ((t.isEmpty())) {
            throw new IllegalArgumentException("Not found");
        }

        if (task.getTaskStatus() == TaskStatus.CANCELLED) {
            throw new IllegalArgumentException("Status is CANCELLED");
        }

        var updated = t.get();
        updated.setTitle(task.getTitle());
        updated.setDescription(task.getDescription());
        updated.setTaskStatus(task.getTaskStatus());
        updated.setAssigneeId(task.getAssigneeId());

        log.info("Update task: " + updated);

        return taskRepository.save(updated);
    }

    public void cancelled(Long id) {
        var t = taskRepository.findById(id);
        if ((t.isEmpty())) {
            throw new IllegalArgumentException("Not found");
        }

        Task task = t.get();
        task.setTaskStatus(TaskStatus.CANCELLED);
        taskRepository.save(task);

        log.info("Cancelled task: " + task);
    }
}