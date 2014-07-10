package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.EntityManager;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

public class DDL {
    private static final Map<String, String> schema;

    static {
        schema = ImmutableMap.of(
                "users", "CREATE TABLE users (" +
                        "id BIGINT(19) NOT NULL auto_increment," +
                        "name VARCHAR(255) NOT NULL default ''," +
                        "version BIGINT(19) NOT NULL default 0," +
                        "created_at TIMESTAMP," +
                        "updated_at TIMESTAMP," +
                        "PRIMARY KEY (id))",

                "categories", "CREATE TABLE categories (" +
                        "id BIGINT(19) NOT NULL auto_increment," +
                        "code VARCHAR(255) NOT NULL default ''," +
                        "name VARCHAR(255) NOT NULL default ''," +
                        "deleted TINYINT(1) NOT NULL default 0," +
                        "created_at TIMESTAMP," +
                        "updated_at TIMESTAMP," +
                        "PRIMARY KEY (id))",

                "articles", "CREATE TABLE articles (" +
                        "id BIGINT(19) NOT NULL auto_increment," +
                        "name VARCHAR(255) NOT NULL default ''," +
                        "title VARCHAR(255) NOT NULL default ''," +
                        "body TEXT," +
                        "status TINYINT(1) NOT NULL default 0," +
                        "deleted TINYINT(1) NOT NULL default 0," +
                        "created_at TIMESTAMP," +
                        "updated_at TIMESTAMP," +
                        "PRIMARY KEY (id))",

                "article_comments", "CREATE TABLE article_comments (" +
                        "id BIGINT(19) NOT NULL auto_increment," +
                        "article_id BIGINT(19) NOT NULL default 0," +
                        "body TEXT," +
                        "status TINYINT(1) NOT NULL default 0," +
                        "deleted TINYINT(1) NOT NULL default 0," +
                        "created_at TIMESTAMP," +
                        "updated_at TIMESTAMP," +
                        "PRIMARY KEY (id))",

                "article_category_rel", "CREATE TABLE article_category_rel (" +
                        "article_id BIGINT(19) NOT NULL default 0," +
                        "category_id BIGINT(19) NOT NULL default 0," +
                        "KEY (article_id)," +
                        "KEY (category_id))"
                );
    }

    public static void resetTables(EntityManager em) {
        em.getTransaction().begin();
        for (Map.Entry<String, String> ddl : schema.entrySet()) {
            em.createNativeQuery("DROP TABLE IF EXISTS " + ddl.getKey())
                    .executeUpdate();
            em.createNativeQuery(ddl.getValue()).executeUpdate();
        }
        em.getTransaction().commit();
    }
}
