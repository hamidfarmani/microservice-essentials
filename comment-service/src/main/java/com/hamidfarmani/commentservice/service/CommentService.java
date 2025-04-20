package com.hamidfarmani.commentservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamidfarmani.commentservice.clients.notification.NotificationClient;
import com.hamidfarmani.commentservice.clients.notification.dto.NotificationResponse;
import com.hamidfarmani.commentservice.model.Comment;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

  private List<Comment> comments;
  private final NotificationClient notificationClient;
  private final ObjectMapper objectMapper;
  private Long nextId = 1L;

  public CommentService(NotificationClient notificationClient, ObjectMapper objectMapper) {
    this.notificationClient = notificationClient;
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void init() {
    try {
      InputStream inputStream = getClass().getResourceAsStream("/data.json");
      comments = objectMapper.readValue(inputStream, new TypeReference<List<Comment>>() {});
    } catch (IOException e) {
      throw new RuntimeException("Failed to load comments from data.json", e);
    }
  }

  public List<Comment> getAllComments() {
    return comments;
  }

  public List<Comment> getCommentsByPostId(Long postId) {
    return comments.stream()
        .filter(comment -> comment.getPostId().equals(postId))
        .collect(Collectors.toList());
  }

  public Comment createComment(Comment comment) {
    comment.setId(nextId++);
    comments.add(comment);
    return comment;
  }

  public boolean deleteComment(Long id) {
    return comments.removeIf(comment -> comment.getId().equals(id));
  }

  public Optional<Comment> updateComment(Long id, Comment updatedComment) {
    return comments.stream()
        .filter(comment -> comment.getId().equals(id))
        .findFirst()
        .map(existingComment -> {
          existingComment.setContent(updatedComment.getContent());
          existingComment.setAuthor(updatedComment.getAuthor());
          existingComment.setUpdatedAt(Instant.now());
          return existingComment;
        });
  }

  public Comment getCommentById(Long id) {
    return comments.stream()
        .filter(comment -> comment.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  public NotificationResponse sendNotification(String message) {
    return notificationClient.sendNotification(message);
  }
} 