package com.github.tachesimazzoca.java.examples.hibernate;

import static org.junit.Assert.*;
import org.junit.Test;

import org.hibernate.Session;

import java.util.List;

public class TableGeneratorTest {
    @Test
    public void testSequenceTable() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = s.createSQLQuery("SHOW COLUMNS FROM hibernate_sequences")
                .list();
        assertEquals(2, rows.size());
        Object[] idInfo = rows.get(0);
        assertEquals("SEQUENCE_NAME", idInfo[0]);
        assertEquals("CHARACTER VARYING(255)", idInfo[1]);
        Object[] nameInfo = rows.get(1);
        assertEquals("NEXT_VAL", nameInfo[0]);
        assertEquals("BIGINT", nameInfo[1]);
        s.getTransaction().commit();
    }
}
