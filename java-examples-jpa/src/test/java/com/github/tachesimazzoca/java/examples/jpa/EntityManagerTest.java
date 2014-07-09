package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class EntityManagerTest {
    @Test
    public void testUsersTable() {
        EntityManager em = JPA.em();
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
        EntityManager em = JPA.em();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
                "SHOW COLUMNS FROM categories").getResultList();
        em.close();

        assertEquals(6, rows.size());
        Set<String> columns = new HashSet<String>();
        for (Object[] row : rows) {
            columns.add((String) row[0]);
        }
        assertTrue(columns.contains("ID"));
        assertTrue(columns.contains("CODE"));
        assertTrue(columns.contains("NAME"));
        assertTrue(columns.contains("DELETED"));
        assertTrue(columns.contains("CREATED_AT"));
        assertTrue(columns.contains("UPDATED_AT"));
    }

    @Test
    public void testArticlesTable() {
        EntityManager em = JPA.em();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(
                "SHOW COLUMNS FROM articles").getResultList();
        em.close();

        assertEquals(8, rows.size());
        Set<String> columns = new HashSet<String>();
        for (Object[] row : rows) {
            columns.add((String) row[0]);
        }
        assertTrue(columns.contains("ID"));
        assertTrue(columns.contains("NAME"));
        assertTrue(columns.contains("TITLE"));
        assertTrue(columns.contains("BODY"));
        assertTrue(columns.contains("STATUS"));
        assertTrue(columns.contains("DELETED"));
        assertTrue(columns.contains("CREATED_AT"));
        assertTrue(columns.contains("UPDATED_AT"));
    }
}
