package org.example.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class TaskList {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

//    @Column(nullable = false)
    String name;

//    @OneToMany(cascade = jakarta.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
//    Set<Task> tasks;

//    @ElementCollection
//    List<Date> updateTimestamps;

    public TaskList() {

    }

    public TaskList(String name) {
        this.name = name;
//        this.tasks = new HashSet<Task>();
//        this.updateTimestamps = new ArrayList<Date>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public Set<Task> getTasks() {
//        return tasks;
//    }

//    public void addToUpdatedTimestamps() {
//        this.updateTimestamps.add(new Date());
//    }

    public Task addToList(Task t) {
//        tasks.add(t);
        return t;
    }
}
