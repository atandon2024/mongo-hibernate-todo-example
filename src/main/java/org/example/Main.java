package org.example;

import org.example.entity.Task;
import org.example.entity.TaskList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) {
        SessionFactory f = new Configuration()
                .configure()
                        .addAnnotatedClass(Task.class)
                .addAnnotatedClass(TaskList.class)
                                .buildSessionFactory();


        try (f) {
            Session s = f.openSession();
            s.beginTransaction();

            // Create new List
            TaskList taskList = new TaskList("My first task list!");
//            Task t = new Task("My first task in a list!");
//            t.setMetadata(new java.util.Date(), "Me");
//            taskList.addToList(t);
//            taskList.addToUpdatedTimestamps();

            // Save to list
            s.persist(taskList);
            s.getTransaction().commit();
//            System.out.println("Saved List with id " + taskList.getId());

//            // Get list and associated tasks
//            s.beginTransaction();
//            s.get(TaskList.class, taskList.getId());
//            System.out.println(taskList.getName());
//            System.out.println(taskList.getTasks());
//            s.getTransaction().commit();

//            s.beginTransaction();
//            Query<TaskList> q = s.createQuery("FROM TaskList ORDER BY id ASC", TaskList.class);
//            q.setMaxResults(1);
//            TaskList firstList = q.uniqueResult();
//            System.out.println(firstList.getName());
//            System.out.println("---");

            s.close();
        }

    }
}