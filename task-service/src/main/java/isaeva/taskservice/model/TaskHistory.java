package isaeva.taskservice.model;


import isaeva.taskservice.enums.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="task_history")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;
    private String username;

    @Enumerated(EnumType.STRING)
    private TaskStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private TaskStatus newStatus;

    private LocalDateTime changedAt;
}
