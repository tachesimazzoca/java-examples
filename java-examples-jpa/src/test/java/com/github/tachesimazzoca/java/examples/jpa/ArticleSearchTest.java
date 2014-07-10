package com.github.tachesimazzoca.java.examples.jpa;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ArticleSearchTest {
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
    public void testSelectWithNumOfComments() {
        EntityManager em = JPA.em();

        int max = 5;
        Set<Long> ids = new HashSet<Long>();
        em.getTransaction().begin();
        for (int i = 0; i < max; i++) {
            Article a = new Article();
            em.persist(a);
            ids.add(a.getId());
        }
        em.getTransaction().commit();

        List<ArticleSearch> rows;
        rows = em.createNamedQuery(
                "selectArticlesWithNumOfComments", ArticleSearch.class)
                .getResultList();
        assertEquals(max, rows.size());
        for (ArticleSearch row : rows) {
            assertEquals(0, row.numOfComments);
        }

        // add x (article.id*2) articleComments to even ID articles
        for (Long id : ids) {
            Article a = em.find(Article.class, id);
            if (id % 2 == 0) {
                em.getTransaction().begin();
                int len = (int) (id * 2);
                for (int i = 0; i < len; i++) {
                    ArticleComment ac = new ArticleComment();
                    a.addArticleComment(ac);
                }
                em.merge(a);
                em.getTransaction().commit();
            }
        }
        rows = em.createNamedQuery(
                "selectArticlesWithNumOfComments", ArticleSearch.class)
                .getResultList();
        for (ArticleSearch row : rows) {
            if (row.id % 2 == 0)
                assertEquals(row.id * 2, row.numOfComments);
            else
                assertEquals(0, row.numOfComments);
        }

        // delete even ID articleComments
        for (Long id : ids) {
            em.getTransaction().begin();
            Article a = em.find(Article.class, id);
            List<ArticleComment> acs = a.getArticleComments();
            for (ArticleComment ac : acs) {
                ac.setDeleted((int) (ac.getId() % 2));
            }
            em.merge(a);
            em.getTransaction().commit();
        }
        rows = em.createNamedQuery(
                "selectArticlesWithNumOfComments", ArticleSearch.class)
                .getResultList();
        for (ArticleSearch row : rows) {
            if (row.id % 2 == 0)
                assertEquals(row.id, row.numOfComments);
            else
                assertEquals(0, row.numOfComments);
        }

        // delete odd ID articles
        for (Long id : ids) {
            Article a = em.find(Article.class, id);
            if (id % 2 == 1) {
                em.getTransaction().begin();
                a.setDeleted(1);
                em.merge(a);
                em.getTransaction().commit();
            }
        }
        rows = em.createNamedQuery(
                "selectArticlesWithNumOfComments", ArticleSearch.class)
                .getResultList();
        assertEquals(2, rows.size());
        for (ArticleSearch row : rows) {
            assertTrue(row.id % 2 == 0);
            assertEquals(row.id, row.numOfComments);
        }

        em.close();
    }
}
