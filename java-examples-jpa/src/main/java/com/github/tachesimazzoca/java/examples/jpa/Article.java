package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
@EntityListeners(VersioningListener.class)
public class Article {
    public enum Status {
        DRAFT(0), PUBLISHED(1);

        private int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status fromValue(int v) {
            for (Status s : Status.values()) {
                if (s.getValue() == v) {
                    return s;
                }
            }
            throw new IllegalArgumentException("unknown value: " + v);
        }
    }

    @Id
    @GeneratedValue
    private long id;

    private String name = "";
    private String title = "";
    private String body;

    @Convert(converter = Article.StatusConverter.class)
    private Status status = Status.DRAFT;

    private int deleted;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Column(name = "updated_at")
    private java.util.Date updatedAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleComment> articleComments = new ArrayList<ArticleComment>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_category_rel",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<Category>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted != 0;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ArticleComment> getArticleComments() {
        return articleComments;
    }

    public void addArticleComment(ArticleComment o) {
        o.setArticle(this);
        getArticleComments().add(o);
    }

    public void removeArticleComments() {
        getArticleComments().clear();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category o) {
        getCategories().add(o);
    }

    public void removeCategories() {
        getCategories().clear();
    }

    public static class StatusConverter implements
            AttributeConverter<Status, Integer> {
        @Override
        public Integer convertToDatabaseColumn(Status status) {
            return status.getValue();
        }

        @Override
        public Status convertToEntityAttribute(Integer value) {
            return Status.fromValue(value);
        }
    }
}
