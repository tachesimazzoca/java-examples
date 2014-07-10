package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

public class UserTest {
    @BeforeClass
    public static void setUp() {
        EntityManager em = JPA.em();
        DDL.resetTables(em);
        em.close();
    }

    @Test
    public void testOptimisticLock() {
        EntityManager em = JPA.em();

        em.getTransaction().begin();
        User a1 = new User();
        a1.setName("test user");
        em.persist(a1);
        em.getTransaction().commit();
        em.refresh(a1);

        EntityManager em2 = JPA.em();
        em2.getTransaction().begin();
        User a2 = em2.find(User.class, a1.getId());
        a2.setName("modified user2");
        em2.merge(a2);
        em2.getTransaction().commit();
        em2.close();

        try {
            em.getTransaction().begin();
            a1.setName("modified user1");
            em.merge(a1);
            em.getTransaction().commit();
            fail("RollbackException should be thrown");
        } catch (javax.persistence.RollbackException e) {
        } catch (Exception e) {
            fail("RollbackException should be thrown");
        } finally {
            assertEquals("modified user1", a1.getName());
            assertEquals("modified user2", a2.getName());
            em.refresh(a1);
            assertEquals("modified user2", a1.getName());
            em.close();
        }
    }
}
