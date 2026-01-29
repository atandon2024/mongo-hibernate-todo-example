package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.TransactionOptions;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.event.CommandFailedEvent;
import com.mongodb.event.CommandListener;
import com.mongodb.event.CommandStartedEvent;
import com.mongodb.event.CommandSucceededEvent;
import com.mongodb.hibernate.service.spi.MongoConfigurationContributor;
import com.mongodb.internal.connection.ProtocolHelper;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.example.entity.*;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.spi.EventType;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main {


    public static void connectDirectlyWithDriver() {
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .automatic(true) // discovers getters/setters/fields automatically
                        .build())
        );

        // Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb://127.0.0.1:27017/mongo-hibernate-perf?directConnection=false";

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString(uri))
                .build();




        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("admin");
            MongoCollection<Document> coll = database.getCollection("embedded-list");


            // Enable failpoint: first insert will fail, triggering a retry
//            database.runCommand(BsonDocument.parse("""
//        { configureFailPoint: "failCommand",
//          mode: { times: 1 },
//          data: { failCommands: ["insert"], closeConnection: true } }
//        """));
//
//            System.out.println("\n== Doing insert with retry enabled ==\n");
//            coll.insertOne(new Document("_id", 123).append("name", "Alice"));
//
//            // Disable failpoint
//            database.runCommand(BsonDocument.parse("{ configureFailPoint: 'failCommand', mode: 'off' }"));


//            List<Movie> r = collection.find().into(new ArrayList<>());
//            for (Movie m : r) {
//                System.out.println(m.toString());
//            }
        }
    }


    public static void main(String[] args) {

//        connectDirectlyWithDriver();


        SessionFactory f = new Configuration()
                .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Task.class)
                .addAnnotatedClass(TaskMetadata.class)
                .addAnnotatedClass(TaskList.class)
                                .buildSessionFactory();




        f.getStatistics().setStatisticsEnabled(true);
        Session s = f.openSession();
        Transaction t = s.getTransaction();
        t.begin();

        try (f) {

            // Create new List
            TaskList taskList = new TaskList("b");
            taskList.setId(1);
            Task task = new Task("task00");
            task.setTitle("task00");
            TaskMetadata metadata = new TaskMetadata("atando", "someTest");
            taskList.setMetadata(metadata);
            taskList.setTags(List.of("a", "b", "c"));
//            taskList.setD(LocalDate.now());


            // Save to list
            s.persist(taskList);
            t.commit();


            // Simple HQL6 months GA
            //Null Ternary Logic - #core functionality
            //Multi-join support - #migration-ease (look into mongo join support, be more specific) -
            //LEFT OUTER JOIN
            //INNER JOIN
            //Support for 7.1
            //Nested Path Expressions in JQL/HQL - #migration-ease, #core-functionality
            //Both Structs and Arrays
            //Comparison Operators in JQL/HQL - #migration-ease, #core-functionality
            //IN and NOT IN (eq top 2)
            //IF NULL, IF NOT NULL
            //NOT LIKE, LIKE
            //Aggregate Operators in JQL/HQL - #migration-ease, #core-functionality
            //SUM
            //AVG
            //GROUP
            //Improved Error handling (Using Hibernate Exceptions with readable and actionable error messages) - #migration-ease, #getting-started
            //Duplicate key error write errors
            //Better Integration with Spring Data - #migration-ease, #getting-started
            //Implement Logging #getting-started #migration-ease
            //Optimistic Locking with @Versioning

            System.out.println("starting test");
            String v = "b";
            String g = "SELECT name FROM TaskList tl";
            String d = g + " WHERE tl.name = '" + v + "'";
            System.out.println(d);
            String a = s.createQuery(d, String.class)
                    .setMaxResults(1).getSingleResult();
            System.out.println(a);
            System.out.println("----1");

            // Native Query
            String nativeQuery = """
            {
              aggregate: "task_lists4",
              pipeline: [
                { $match: { name: "b" } },
                { $group: {
                    _id: "$name",
                    count: { $sum: 1 }
                }},
                { $project: { _id: 0, name: 1, count: 1 } }
              ]
            }
            """;


            var results = s.createNativeQuery(nativeQuery, TaskListDTO.class)
                    .getResultList();

            System.out.println(results.get(0).getCount());
        }



//            String q = """
//                {
//                    "find": "taskList"
//                }
//            """;
//
//            s.doWork(connection -> {
//                PreparedStatement ps = connection.prepareStatement(q);
//                ps.setString(1, "123");
//                ResultSet rs = ps.executeQuery();
//                System.out.println(rs.next());
//                System.out.println("----");
//                // process result set
//            });
//
//            // Native Query
//            List<TaskList> r = s.createNativeQuery(q, TaskList.class).getResultList();
//            System.out.println(r.get(0).toString());
//
            // Look up using HQL
//            String hql = "FROM TaskList u WHERE CASE WHEN u.uselessNum = 2 THEN 5 ELSE 4 END";
//            Query<TaskList> hqlQuery = s.createQuery(hql, TaskList.class);
//            ScrollableResults<TaskList> results = hqlQuery.scroll(ScrollMode.FORWARD_ONLY);
//            while(results.next()) {
//                TaskList tl = results.get();
//                System.out.println("Found by HQL with scroll:");
//                System.out.println(tl.toString());
//            }
//
//            // Named queries test
//            List<TaskList> namedQueryResults = s.createNamedQuery("TaskList.namedQueryTest").getResultList();
//            System.out.println("Found by named query:");
//            System.out.println(namedQueryResults.get(0).toString());
//
//
//            // Look up using HQL using natural id
//            TaskList tl_by_nid = s.byNaturalId(TaskList.class).using("email", "atando").load();
//            System.out.println("Found by natural id:");
//            System.out.println(tl_by_nid.toString());
//
//
//
//            // Look up using native JPQL
//            String jpql = "SELECT a FROM TaskList a WHERE a.name = :name";
//            TypedQuery<TaskList> jpqlQuery = s.createQuery(jpql, TaskList.class);
//            jpqlQuery.setParameter("name", "My first task list!");
//
//            List<TaskList> lists = jpqlQuery.getResultList();
//            System.out.println(lists);
//
//            // Criteria builder
//            CriteriaBuilder cb = s.getCriteriaBuilder();
//            CriteriaQuery<TaskList> cq = cb.createQuery(TaskList.class);
//            Root<TaskList> root = cq.from(TaskList.class);
//
//            cq.where(cb.equal(root.get("name"), "My first task list!"));
//            TypedQuery<TaskList> typedQuery = s.createQuery(cq);
//            List<TaskList> list = typedQuery.getResultList();
//            System.out.println(list.get(0).toString());
//            System.out.println(list.get(0).getId());
//            System.out.println("------");
//
//            // Use DTO when retrieving Task List
//            String jpqlDTO = "SELECT new org.example.entity.TaskListDTO(tl.name) FROM TaskList tl WHERE tl.name = :name";
//            TypedQuery<TaskListDTO> jpqlQueryDTO = s.createQuery(jpqlDTO, TaskListDTO.class);
//            jpqlQueryDTO.setParameter("name", "My first task list!");
//            s.evict(taskList);
//
//            List<TaskListDTO> listsDTO = jpqlQueryDTO.getResultList();
//            System.out.println(listsDTO.get(0).getName());
//
//            taskListService.setSession(s);
//            taskListService.registerNewTaskList("My second task list!");
//            System.out.println("Transactional registered!");
//
//            // Force rollback of commits
//            throw new UnsupportedOperationException("unsupported");
//unsupported        } catch (Exception e) {
//            throw e;
//            System.out.println(e.getMessage());
//            System.out.println("Caught an exception, rolling back.");
//            t.rollback();
//            System.out.println("Rollback successful");
//            System.out.println("Entity fetch count: " + f.getStatistics().getEntityFetchCount());
//            System.out.println("Second-level cache hit count: " + f.getStatistics().getSecondLevelCacheHitCount());
//            s.close();
//        }

    }
}