package org.example.entity;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class TaskMetadata {
    Date date;
    String author;
}
