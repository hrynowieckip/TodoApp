package model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "task_list")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String taskListName;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createdOn;

    public TaskList() {
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public TaskList(String taskListName) {
        this.taskListName = taskListName;
    }

    public Long getId() {
        return id;
    }

    public String getTaskListName() {
        return taskListName;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", taskListName='" + taskListName + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
