package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "categories")
@EntityListeners(Category.CategoryListener.class)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Pattern(regexp = "^[0-9]+(-[0-9]+)*$")
    @Column(columnDefinition = "VARCHAR(255) NOT NULL default ''")
    private String code;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL default ''")
    private String name;

    @Column(columnDefinition = "TINYINT(1) NOT NULL default 0")
    private int deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted != 1;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        if (deleted != 0)
            this.deleted = 1;
        else
            this.deleted = 0;
    }

    @PostLoad
    public void postLoad() {
        System.out.println("postLoaded");
    }

    public static class CategoryListener {
        @PrePersist
        public void prePersist(Category a) {
            System.out.println("prePersisted");
        }
    }
}
