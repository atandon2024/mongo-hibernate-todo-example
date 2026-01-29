package org.example.entity;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class TaskListId {

    private Long id;
    private String name;

    public TaskListId() {
    }

    public TaskListId(Long id, String name) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.name = name;
    }
}
