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
    private TaskList todos;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdOn;
    private Boolean completed;

    public Task(String name) {
        this.name = name;
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
}
