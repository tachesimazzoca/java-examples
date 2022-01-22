package com.github.tachesimazzoca.java.examples.hibernate;

import static org.junit.Assert.*;
import org.junit.Test;

import org.hibernate.Session;

import java.util.List;

public class UserTest {
    @Test
    public void testTable() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = s.createSQLQuery("SHOW COLUMNS FROM users")
                .list();
        assertEquals(4, rows.size());
        Object[] idInfo = rows.get(0);
        assertEquals("ID", idInfo[0]);
        assertEquals("BIGINT", idInfo[1]);
        Object[] nameInfo = rows.get(1);
        assertEquals("NAME", nameInfo[0]);
        assertEquals("CHARACTER VARYING(255)", nameInfo[1]);
        Object[] createdAtInfo = rows.get(2);
        assertEquals("CREATED_AT", createdAtInfo[0]);
        assertEquals("TIMESTAMP", createdAtInfo[1]);
        Object[] updatedAtInfo = rows.get(3);
        assertEquals("UPDATED_AT", updatedAtInfo[0]);
        assertEquals("TIMESTAMP", updatedAtInfo[1]);
        s.getTransaction().commit();
    }

    @Test
    public void testSaveAndLoad() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        s.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
        int max = 10;
        long created = System.currentTimeMillis();
        for (int n = 1; n <= max; n++) {
            User a = new User();
            a.setName("Created User" + n);
            a.setCreatedAt(new java.util.Date(created));
            a.setUpdatedAt(new java.util.Date(created));
            s.save(a);
        }
        s.getTransaction().commit();

        s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        long updated = System.currentTimeMillis();
        for (int n = 1; n <= max; n++) {
            long id = (long) n;
            User a = (User) s.load(User.class, id);
            assertEquals(id, a.getId());
            assertEquals("Created User" + id, a.getName());
            assertEquals(created, a.getCreatedAt().getTime());
            assertEquals(created, a.getUpdatedAt().getTime());

            a.setName("Updated User" + a.getId());
            a.setUpdatedAt(new java.util.Date(updated));
            s.save(a);
        }
        s.getTransaction().commit();

        s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        @SuppressWarnings("unchecked")
        List<User> users = s.createQuery("from User").list();
        s.getTransaction().commit();
        assertEquals(max, users.size());
        for (User user : users) {
            long id = user.getId();
            assertEquals("Updated User" + id, user.getName());
            assertEquals(created, user.getCreatedAt().getTime());
            assertEquals(updated, user.getUpdatedAt().getTime());
        }
    }
}
