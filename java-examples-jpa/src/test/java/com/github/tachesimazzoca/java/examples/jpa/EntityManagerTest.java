package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class EntityManagerTest {
    @BeforeClass
    public static void setUp() {
        EntityManager em = JPA.em();
        DDL.resetTables(em);
        em.close();
    }

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
        assertTrue(columns.contains("id"));
        assertTrue(columns.contains("name"));
    }
}
