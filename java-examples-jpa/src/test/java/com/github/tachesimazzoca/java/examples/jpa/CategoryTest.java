package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.Test;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class CategoryTest {
    private static EntityManagerFactory ef =
            Persistence.createEntityManagerFactory("default");

    @Test
    public void testPersistAndFind() {
        EntityManager em = ef.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (int n = 1; n <= 5; n++) {
            Category a = new Category();
            a.setCode(String.format("100-%03d", n));
            a.setName("category" + n);
            a.setDeleted(0);
            em.persist(a);
            em.flush();
        }
        tx.commit();

        for (int n = 1; n <= 5; n++) {
            Category a = em.find(Category.class, (long) n);
            assertEquals(n, a.getId());
            assertEquals("category" + n, a.getName());
            assertEquals(String.format("100-%03d", n), a.getCode());
        }
        em.close();
    }
}
