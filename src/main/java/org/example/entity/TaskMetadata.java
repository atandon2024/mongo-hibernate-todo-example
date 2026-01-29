package org.example.entity;

import com.mongodb.hibernate.annotations.ObjectIdGenerator;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.bson.json.JsonObject;
import org.bson.types.ObjectId;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Date;

@Entity
public class TaskMetadata {
    @Id
    @ObjectIdGenerator
    ObjectId id;


    String author;

    String someTest;


    public TaskMetadata() {
    }

    public TaskMetadata(String author, String someTest) {
        this.author = author;
        this.someTest = someTest;
    }


}
