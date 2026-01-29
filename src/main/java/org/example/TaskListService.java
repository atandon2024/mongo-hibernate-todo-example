package org.example;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.entity.TaskList;
import org.hibernate.Session;

public class TaskListService {

    @PersistenceContext
    private Session session;

    @Transactional
    public void registerNewTaskList(String name) {
        this.session.persist(new TaskList(name));
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
