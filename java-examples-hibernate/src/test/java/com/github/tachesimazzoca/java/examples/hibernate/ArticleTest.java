package com.github.tachesimazzoca.java.examples.hibernate;

import static org.junit.Assert.*;
import org.junit.Test;

import org.hibernate.Session;

import java.util.List;

public class ArticleTest {
    @Test
    public void testTable() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = s.createSQLQuery("SHOW COLUMNS FROM articles")
                .list();
        assertEquals(5, rows.size());
        Object[] idInfo = rows.get(0);
        assertEquals("ID", idInfo[0]);
        assertEquals("BIGINT", idInfo[1]);
        Object[] titleInfo = rows.get(1);
        assertEquals("TITLE", titleInfo[0]);
        assertEquals("CHARACTER VARYING(255)", titleInfo[1]);
        Object[] bodyInfo = rows.get(2);
        assertEquals("BODY", bodyInfo[0]);
        assertEquals("CHARACTER VARYING", bodyInfo[1]);
        Object[] createdAtInfo = rows.get(3);
        assertEquals("CREATED_AT", createdAtInfo[0]);
        assertEquals("TIMESTAMP", createdAtInfo[1]);
        Object[] updatedAtInfo = rows.get(4);
        assertEquals("UPDATED_AT", updatedAtInfo[0]);
        assertEquals("TIMESTAMP", updatedAtInfo[1]);
        s.getTransaction().commit();
    }

    @Test
    public void testSaveAndLoad() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        int max = 10;
        long created = System.currentTimeMillis();
        for (int n = 1; n <= max; n++) {
            Article a = new Article();
            a.setTitle("title" + n);
            a.setBody("body" + n);
            a.setCreatedAt(new java.util.Date(created));
            a.setUpdatedAt(new java.util.Date(created));
            s.save(a);
        }
        s.getTransaction().commit();
        
        s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        long updated = System.currentTimeMillis();
        long offset = 100;
        for (int n = 1; n <= max; n++) {
            long id = offset + n;
            Article a = (Article) s.load(Article.class, id);
            assertEquals(id, a.getId());
            assertEquals("title" + n, a.getTitle());
            assertEquals("body" + n, a.getBody());
            assertEquals(created, a.getCreatedAt().getTime());
            assertEquals(created, a.getUpdatedAt().getTime());

            a.setTitle("updated title" + n);
            a.setUpdatedAt(new java.util.Date(updated));
            s.save(a);
        }
        s.getTransaction().commit();

        s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        @SuppressWarnings("unchecked")
        List<Article> articles = s.createQuery("from Article").list();
        s.getTransaction().commit();
        assertEquals(max, articles.size());
        for (Article article : articles) {
            long id = article.getId();
            assertEquals("updated title" + (id % offset), article.getTitle());
            assertEquals(created, article.getCreatedAt().getTime());
            assertEquals(updated, article.getUpdatedAt().getTime());
        }
    }
}
