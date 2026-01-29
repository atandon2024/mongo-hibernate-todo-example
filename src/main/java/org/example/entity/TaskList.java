package org.example.entity;

import com.mongodb.MongoClientSettings;
import com.mongodb.hibernate.annotations.ObjectIdGenerator;
import jakarta.persistence.*;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;


import org.bson.json.JsonObject;
import org.bson.types.ObjectId;
import org.example.UpperCaseStringType;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


@Entity
@Table(name = "task_lists4")
public class TaskList {

    @Id
    private int id;

    private String name;

    private LocalDate d;

    @Column
    private String email;

    @OrderBy("createdAt DESC")
    List<String> tags = new ArrayList<String>();

    @Embedded @Struct(name="metadataaaaa")
    TaskMetadata metadata;

    public TaskList() {
    }

    public TaskList(String name) {
        this.setName(name);


        TaskMetadata metadata = new TaskMetadata("atando", "test2");

        this.setEmail("atandonn");;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setD(LocalDate d) {
        this.d = d;
    }


    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMetadata(TaskMetadata metadata) {
        this.metadata = metadata;
    }

    public TaskMetadata getMetadata() {
        return metadata;
    }
}
