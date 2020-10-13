package model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_list")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskListName;
    @OneToMany(mappedBy = "task_list")
    private List<Task> tasks =  new ArrayList<>();
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createdOn;

    public Long getId() {
        return id;
    }

    public String getTaskListName() {
        return taskListName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }
}
