package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.ImmutableSet;

public class ArticleTest {
    @BeforeClass
    public static void setUp() {
        EntityManager em = JPA.em();
        DDL.resetTables(em);
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

        int max = 5;
        Long[] ids = new Long[max];
        for (int i = 0; i < max; i++) {
            int n = i + 1;
            em.getTransaction().begin();
            Category cat = new Category();
            cat.setCode(String.format("1%04d", n));
            cat.setName(String.format("category%d", n));
            em.persist(cat);
            em.getTransaction().commit();
            ids[i] = cat.getId();
        }
        em.getTransaction().begin();
        for (int i = 0; i < ids.length; i++) {
            a1.addCategory(em.find(Category.class, ids[i]));
        }
        em.merge(a1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        ImmutableSet.Builder<Long> ids2 = ImmutableSet.builder();
        for (int i = 0; i < ids.length; i += 2) {
            a2.addCategory(em.find(Category.class, ids[i]));
            ids2.add(ids[i]);
        }
        em.merge(a2);
        em.getTransaction().commit();

        a1 = em.find(Article.class, a1.getId());
        Set<Long> categories1 = ImmutableSet.copyOf(
                Iterables.transform(a1.getCategories(),
                        new Function<Category, Long>() {
                            public Long apply(Category a) {
                                return a.getId();
                            }
                        }));
        assertEquals(ImmutableSet.<Long> copyOf(ids), categories1);

        a2 = em.find(Article.class, a2.getId());
        Set<Long> categories2 = ImmutableSet.copyOf(
                Iterables.transform(a2.getCategories(),
                        new Function<Category, Long>() {
                            public Long apply(Category a) {
                                return a.getId();
                            }
                        }));
        assertEquals(ids2.build(), categories2);

        em.close();
    }
}
