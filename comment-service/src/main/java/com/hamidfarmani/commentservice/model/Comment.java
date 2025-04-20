package com.hamidfarmani.commentservice.model;

import java.time.Instant;

public class Comment {
    private Long id;
    private Long postId;
    private String content;
    private String author;
    private Instant createdAt;
    private Instant updatedAt;

    public Comment() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Comment(Long postId, String content, String author) {
        this();
        this.postId = postId;
        this.content = content;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
} 