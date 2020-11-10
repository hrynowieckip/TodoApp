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
    @ManyToOne
    private TaskList todos;
    @Column(name = "todos_id", insertable = false,updatable = false)
    private Long todosId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdOn;
    private Boolean completed;

    public Task(){}
    public Task(String name) {
        this.name = name;
    }
    public Task(String name, TaskList task, Long taskId, boolean completed) {
        this.name = name;
        this.todos=task;
        this.todosId=taskId;
        this.completed=completed;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
