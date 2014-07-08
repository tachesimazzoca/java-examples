package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.Test;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class EntityManagerTest {
    private static final EntityManagerFactory ef =
            Persistence.createEntityManagerFactory("default");

    @Test
    public void testUsersTable() {
        EntityManager em = ef.createEntityManager();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
                "SHOW COLUMNS FROM users").getResultList();
        em.close();

        assertEquals(2, rows.size());
        Set<String> columns = new HashSet<String>();
        for (Object[] row : rows) {
            columns.add((String) row[0]);
        }
        assertTrue(columns.contains("ID"));
        assertTrue(columns.contains("NAME"));
    }

    @Test
    public void testCategoriesTable() {
        EntityManager em = ef.createEntityManager();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
                "SHOW COLUMNS FROM categories").getResultList();
        em.close();

        assertEquals(4, rows.size());
        Set<String> columns = new HashSet<String>();
        for (Object[] row : rows) {
            columns.add((String) row[0]);
        }
        assertTrue(columns.contains("ID"));
        assertTrue(columns.contains("CODE"));
        assertTrue(columns.contains("NAME"));
        assertTrue(columns.contains("DELETED"));
    }
}
