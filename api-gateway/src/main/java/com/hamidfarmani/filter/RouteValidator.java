package com.hamidfarmani.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/auth/register",
            "/auth/login",
            "/eureka",
            "/actuator/health",
            "/actuator/info",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    );

    public Predicate<ServerHttpRequest> isSecured = this::isSecuredEndpoint;

    private boolean isSecuredEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return OPEN_API_ENDPOINTS.stream()
                .noneMatch(endpoint -> matchPath(path, endpoint));
    }

    private boolean matchPath(String path, String pattern) {
        if (pattern.endsWith("/**")) {
            String prefix = pattern.substring(0, pattern.length() - 3);
            return path.startsWith(prefix);
        }
        return path.equals(pattern);
    }
}
