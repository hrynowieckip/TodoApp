package model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_list_id", nullable = false)
    private TaskList taskList;
    @Column(name = "task_list_id", insertable = false,updatable = false)
    private Long taskListId;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdOn;
    private Boolean completed;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
