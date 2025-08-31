package isaeva.taskservice.model;


import isaeva.taskservice.enums.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    private String username;

    @Enumerated(EnumType.STRING)
    private TaskStatus previousStatus;

    @Enumerated(EnumType.STRING)
    private TaskStatus newStatus;

    private LocalDateTime changedAt;
}
