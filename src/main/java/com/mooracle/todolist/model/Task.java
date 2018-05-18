package com.mooracle.todolist.model;

import javax.persistence.*;

/** ENTRY LIST:
 *  ENTRY 14: Associating task to user
 *
 *  */

@Entity
public class Task {
    /** 14-1:
     *  1. we need to associate the task to a user thus we need to add field User in this Task Entity
     *  2. then we give it appropriate annotation*/
    //field declaration
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private boolean complete;

    @ManyToOne //14-2: add to denotes many task (here) can be associated to one user
    @JoinColumn(name = "user_id")//14-3: associating by adding user id column in the Task table.
    private User user;//14-1: build user field

    //build default (empty) constructor
    public Task() {
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    //14-4: build getters and setters for user field so that JPA has access to it

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
