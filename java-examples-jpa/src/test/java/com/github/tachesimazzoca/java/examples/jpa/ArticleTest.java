package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ArticleTest {
    @BeforeClass
    public static void setUp() {
        EntityManager em = JPA.em();
        DDL.resetTables(em);
        em.close();
    }

    @Before
    public void beforeTest() {
        EntityManager em = JPA.em();
        DDL.clearTables(em);
        em.close();
    }

    @Test
    public void testSaveAndLoad() {
        EntityManager em = JPA.em();

        em.getTransaction().begin();
        for (int n = 1; n <= 5; n++) {
            Article a = new Article();
            a.setName("article" + n);
            a.setTitle("title" + n);
            a.setBody("body" + n);
            a.setStatus(Article.Status.PUBLISHED);
            em.persist(a);
            em.flush();
        }
        em.getTransaction().commit();

        for (int n = 1; n <= 5; n++) {
            Article a = em.find(Article.class, (long) n);
            assertEquals(n, a.getId());
            assertEquals("article" + n, a.getName());
            assertEquals("title" + n, a.getTitle());
            assertEquals("body" + n, a.getBody());
            assertEquals(Article.Status.PUBLISHED, a.getStatus());
            assertNotNull(a.getCreatedAt());
            assertNotNull(a.getUpdatedAt());
            assertTrue(a.getCreatedAt().equals(a.getUpdatedAt()));
        }

        em.getTransaction().begin();
        for (int n = 1; n <= 5; n++) {
            Article a = em.find(Article.class, (long) n);
            a.setTitle("updated title" + n);
            if (n % 2 == 0)
                a.setStatus(Article.Status.DRAFT);
            em.merge(a);
            em.flush();
        }
        em.getTransaction().commit();

        for (int n = 1; n <= 5; n++) {
            Article a = em.find(Article.class, (long) n);
            assertEquals(n, a.getId());
            assertEquals("updated title" + n, a.getTitle());
            if (n % 2 == 0)
                assertEquals(Article.Status.DRAFT, a.getStatus());
            else
                assertEquals(Article.Status.PUBLISHED, a.getStatus());
            assertNotNull(a.getCreatedAt());
            assertNotNull(a.getUpdatedAt());
            assertTrue(a.getCreatedAt().getTime() <= a.getUpdatedAt().getTime());
        }

        @SuppressWarnings("unchecked")
        List<Article> rows = (List<Article>) em.createQuery(
                "SELECT a FROM Article AS a WHERE a.status = ?1")
                .setParameter(1, Article.Status.PUBLISHED).getResultList();
        assertEquals(3, rows.size());
        for (Article row : rows) {
            assertTrue(row.getId() % 2 != 0);
        }
        em.close();
    }

    @Test
    public void testArticleComment() {
        EntityManager em = JPA.em();

        em.getTransaction().begin();
        Article a = new Article();
        em.persist(a);
        em.getTransaction().commit();

        for (int i = 0; i < 5; i++) {
            em.refresh(a);
            em.getTransaction().begin();
            ArticleComment ac = new ArticleComment();
            ac.setBody(String.format("comment-%d-%d", a.getId(), ac.getId()));
            a.addArticleComment(ac);
            em.merge(a);
            em.getTransaction().commit();
        }

        List<ArticleComment> comments = a.getArticleComments();
        assertEquals(5, comments.size());

        em.getTransaction().begin();
        a.removeArticleComments();
        em.merge(a);
        em.getTransaction().commit();
        assertEquals(0, a.getArticleComments().size());

        @SuppressWarnings("unchecked")
        List<ArticleComment> rows = (List<ArticleComment>) em.createQuery(
                "SELECT a FROM ArticleComment AS a WHERE a.article = ?1")
                .setParameter(1, a).getResultList();
        assertEquals(0, rows.size());

        em.close();
    }

    @Test
    public void testCategory() {
        EntityManager em = JPA.em();

        em.getTransaction().begin();
        Article a1 = new Article();
        em.persist(a1);
        Article a2 = new Article();
        em.persist(a2);
        em.getTransaction().commit();

        Set<Long> ids = new HashSet<Long>();
        for (int i = 0; i < 5; i++) {
            int n = i + 1;
            em.getTransaction().begin();
            Category cat = new Category();
            cat.setCode(String.format("1%04d", n));
            cat.setName(String.format("category%d", n));
            em.persist(cat);
            em.getTransaction().commit();
            ids.add(cat.getId());
        }
        em.getTransaction().begin();
        for (Long id : ids) {
            a1.addCategory(em.find(Category.class, id));
        }
        em.merge(a1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Set<Long> ids2 = new HashSet<Long>();
        for (Long id : ids) {
            if (id % 2 != 0)
                continue;
            a2.addCategory(em.find(Category.class, id));
            ids2.add(id);
        }
        em.merge(a2);
        em.getTransaction().commit();

        Set<Long> categories1 = new HashSet<Long>();
        a1 = em.find(Article.class, a1.getId());
        for (Category cat : a1.getCategories()) {
            categories1.add(cat.getId());
        }
        assertEquals(ids, categories1);

        Set<Long> categories2 = new HashSet<Long>();
        a2 = em.find(Article.class, a2.getId());
        for (Category cat : a2.getCategories()) {
            categories2.add(cat.getId());
        }
        assertEquals(ids2, categories2);

        em.close();
    }
}
