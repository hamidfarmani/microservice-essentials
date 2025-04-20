package com.hamidfarmani.commentservice.clients.notification;

import com.hamidfarmani.commentservice.clients.notification.dto.NotificationResponse;

public interface NotificationClient {
    NotificationResponse sendNotification(String message);
    NotificationResponse getNotification();
} 