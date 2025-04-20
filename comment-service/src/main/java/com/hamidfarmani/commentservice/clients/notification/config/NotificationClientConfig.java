package com.hamidfarmani.commentservice.clients.notification.config;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class NotificationClientConfig {

    private static final String NOTIFICATION_SERVICE = "NOTIFICATION-SERVICE";

    @Bean
    public RestClient.Builder notificationRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient notificationRestClient(LoadBalancerClient loadBalancerClient, RestClient.Builder restClientBuilder) {
        String serviceUrl = loadBalancerClient.choose(NOTIFICATION_SERVICE).getUri().toString();
        return restClientBuilder
                .baseUrl(serviceUrl + "/api")
                .build();
    }
} 