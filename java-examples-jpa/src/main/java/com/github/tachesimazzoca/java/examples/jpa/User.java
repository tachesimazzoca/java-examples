package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "users")
public class User {
    private long id;
    private String name;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(columnDefinition = "VARCHAR(255) NOT NULL default ''")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
