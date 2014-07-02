package com.github.tachesimazzoca.java.examples.hibernate;

import static org.junit.Assert.*;
import org.junit.Test;

import org.hibernate.Session;

import java.util.List;

public class SessionTest {
    @Test
    @SuppressWarnings("unchecked")
    public void testSession() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        List<Object[]> items = s.createSQLQuery("SELECT 1, 2, 3").list();
        s.getTransaction().commit();
        assertEquals(1, items.size());
        assertEquals(1, (int) items.get(0)[0]);
        assertEquals(2, (int) items.get(0)[1]);
        assertEquals(3, (int) items.get(0)[2]);
    }
}
