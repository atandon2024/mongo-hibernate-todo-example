package org.example.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    TaskList taskList;

//    @Column(nullable = false)
    String description;

    @Embedded
    TaskMetadata metadata;

    public Task() {
    }

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public TaskMetadata setMetadata(Date date, String author) {
        metadata = new TaskMetadata();
        metadata.date = date;
        metadata.author = author;
        return metadata;
    }

    public Long getId() {
        return id;
    }
}
