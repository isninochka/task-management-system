package isaeva.taskservice.repository;

import isaeva.taskservice.enums.TaskStatus;
import isaeva.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsername(String username);

    List<Task> findByUsernameAndTaskStatusNot(String username, TaskStatus status);
}
