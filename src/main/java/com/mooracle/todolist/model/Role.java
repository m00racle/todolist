package com.mooracle.todolist.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/** ENTRY LIST:
 *  ENTRY 2: Users and Roles
 *
 *  */

@Entity //2-1: put this annotation indicating this is an Entity
public class Role {
    //2-2: field declaration:
    @Id //2-3:annotate as id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //2-4: generated value automatically as id
    private Long id;

    private String name;

    //2-5: getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
