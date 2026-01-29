package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Struct;

@Embeddable
@Struct(name = "Task")
public class Task {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @Column(nullable = true)
    private String title;
//
//    @Embedded
//    private TaskMetadata metadata;

    public Task() {
    }

    public Task(String title) {
        this.setTitle(title);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
