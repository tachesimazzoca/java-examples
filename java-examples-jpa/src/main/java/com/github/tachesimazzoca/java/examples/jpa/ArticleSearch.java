package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
        name = "selectArticlesWithNumOfComments",
        query = "SELECT new ArticleSearch(a.id, COUNT(ac))" +
                " FROM Article AS a" +
                " LEFT OUTER JOIN a.articleComments AS ac" +
                " ON ac.deleted <> 1" +
                " WHERE a.deleted <> 1" +
                " GROUP BY a.id")
public class ArticleSearch {
    @Id
    public final long id;
    public final long numOfComments;

    public ArticleSearch(long id, long numOfComments) {
        this.id = id;
        this.numOfComments = numOfComments;
    }
}
