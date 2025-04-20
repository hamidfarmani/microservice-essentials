package com.hamidfarmani.notificationservice.controller;

import com.hamidfarmani.notificationservice.dto.NotificationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @PostMapping("/send")
    public NotificationResponse sendNotification(@RequestBody String message) {
        return new NotificationResponse("Notification with message: " + message + " sent successfully!", "SUCCESS");
    }

    @GetMapping
    public String getNotification() {
        return "This is a GET request to the notification service";
    }
} 