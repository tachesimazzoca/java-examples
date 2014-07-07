package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.Test;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

public class EntityManagerTest {
    @Test
    public void testEntityManagerFactory() {
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("default");
        EntityManager em = ef.createEntityManager();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery("SHOW COLUMNS FROM users").getResultList();
        assertEquals(2, rows.size());
        Object[] column;
        column = rows.get(0);
        assertEquals("ID", column[0]);
        assertEquals("BIGINT(19)", column[1]);
        column = rows.get(1);
        assertEquals("NAME", column[0]);
        assertEquals("VARCHAR(255)", column[1]);
    }
}
