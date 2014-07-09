package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JPA {
    private static final EntityManagerFactory ef;

    static {
        ef = Persistence.createEntityManagerFactory("default");
    }

    public static EntityManager em() {
        return ef.createEntityManager();
    }
}
