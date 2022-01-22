package com.github.tachesimazzoca.java.examples.hibernate;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.hibernate.Session;
import org.junit.Test;

public class SessionTest {
    @Test
    @SuppressWarnings("unchecked")
    public void testSession() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        List<Object[]> items = s.createSQLQuery("SELECT 1, 2, 3").list();
        s.getTransaction().commit();
        assertEquals(1, items.size());
        Object[] rows = items.get(0);
        assertEquals("1", String.valueOf(rows[0]));
        assertEquals("2", String.valueOf(rows[1]));
        assertEquals("3", String.valueOf(rows[2]));
    }
}
