package org.example.entity;

import org.bson.types.ObjectId;

public class TaskListDTO {

    String name;

    Integer count;

    public TaskListDTO(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public TaskListDTO() {

    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }
}
