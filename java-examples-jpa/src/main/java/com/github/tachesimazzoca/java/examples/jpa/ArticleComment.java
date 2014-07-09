package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Entity
@Table(name = "article_comments")
@EntityListeners(VersioningListener.class)
public class ArticleComment {
    public enum Status {
        POSTED(0), ALLOWED(1), REJECTED(2);

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private String body;

    @Convert(converter = ArticleComment.StatusConverter.class)
    private Status status = Status.POSTED;

    private int deleted;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Column(name = "updated_at")
    private java.util.Date updatedAt;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
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
