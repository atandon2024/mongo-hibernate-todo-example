package org.example.entity;

import jakarta.persistence.Entity;

@Entity
public class Movie {
    String title;

    // Public no-arg constructor
    public Movie() {}

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return "Movie{title='" + title + "'}";
    }

}
