package com.hamidfarmani.commentservice.clients.notification;

import com.hamidfarmani.commentservice.clients.notification.dto.NotificationResponse;
import com.hamidfarmani.commentservice.clients.notification.exception.NotificationServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class NotificationClientImpl implements NotificationClient {
    private static final Logger log = LoggerFactory.getLogger(NotificationClientImpl.class);
    private static final String NOTIFICATIONS_PATH = "/notifications";
    private static final String SEND_NOTIFICATION_PATH = NOTIFICATIONS_PATH + "/send";

    private final RestClient notificationRestClient;

    public NotificationClientImpl(RestClient notificationRestClient) {
        this.notificationRestClient = notificationRestClient;
    }

    @Override
    public NotificationResponse sendNotification(String message) {
        try {
            log.debug("Sending notification with message: {}", message);
            return notificationRestClient.post()
                    .uri(SEND_NOTIFICATION_PATH)
                    .body(message)
                    .retrieve()
                    .body(NotificationResponse.class);
        } catch (RestClientException e) {
            log.error("Failed to send notification: {}", e.getMessage());
            throw new NotificationServiceException("Failed to send notification", e);
        }
    }

    @Override
    public NotificationResponse getNotification() {
        try {
            log.debug("Getting a notification");
            var response = notificationRestClient.get()
                    .uri(NOTIFICATIONS_PATH)
                    .retrieve()
                    .toEntity(String.class);

            return new NotificationResponse(
                    response.getBody(),
                    response.getStatusCode().equals(HttpStatus.OK) ? "SUCCESS" : "FAILED"
            );
        } catch (RestClientException e) {
            log.error("Failed to get a notification: {}", e.getMessage());
            throw new NotificationServiceException("Failed to get a notification", e);
        }
    }
} 