package com.hamidfarmani.commentservice.controller;

import com.hamidfarmani.commentservice.clients.notification.dto.NotificationResponse;
import com.hamidfarmani.commentservice.model.Comment;
import com.hamidfarmani.commentservice.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("/notification")
    public NotificationResponse sendNotification(@RequestBody String message) {
        return commentService.sendNotification(message);
    }
} 