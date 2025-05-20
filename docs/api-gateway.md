# API Gateway Service

The API Gateway acts as the single entry point for all client requests in the microservices architecture. It handles routing, authentication, load balancing, circuit breaking, and observability.

## Features

- JWT-based authentication and security
- Client-side load balancing (Spring Cloud LoadBalancer)
- Circuit breaking (Resilience4j)
- Distributed tracing (Zipkin)
- Metrics and monitoring (Micrometer, Prometheus)
- Centralized logging with trace IDs

## Key Endpoints

- `/auth/register`, `/auth/login` (public)
- `/eureka` (public)
- All other endpoints require JWT authentication

## Observability

- Distributed tracing with Zipkin
- Metrics via `/actuator/prometheus`
- Health checks via `/actuator/health`
- Logging includes trace IDs for request tracking

## Configuration

- Redis for caching
- Circuit breaker settings
- Exposes actuator endpoints for health, metrics, and more

## Monitoring

- Integrates with Grafana for dashboards
- Alerts for latency, circuit breaker trips, error rates, and memory usage

For more details, see the [root documentation](./root.md).
