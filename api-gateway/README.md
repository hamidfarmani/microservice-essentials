# API Gateway Service

The API Gateway service acts as the single entry point for all client requests in the microservices architecture. It handles cross-cutting concerns and provides essential features for managing and monitoring the microservices ecosystem.

## Features

### 1. Authentication & Security

- JWT-based authentication
- Secure endpoints with token validation
- Whitelisted public endpoints:
  - `/auth/register`
  - `/auth/login`
  - `/eureka`

### 2. Load Balancing

- Client-side load balancing using Spring Cloud LoadBalancer
- Integration with Eureka for service discovery
- Automatic routing using the `lb://` scheme

### 3. Circuit Breaking

- Implemented using Resilience4j
- Default circuit breaker configuration:
  - 50% failure threshold
  - 10-request sliding window (count-based)
  - 10-second wait duration in open state
  - 5-second timeout for requests
  - Automatic transition from open to half-open state
  - 3 permitted calls in half-open state

### 4. Observability

### 1. Distributed Tracing with Zipkin

Zipkin provides distributed tracing to help you track requests across multiple services.

#### Accessing Zipkin UI

```bash
# Open in your browser
http://localhost:9411

# Check if Zipkin is running
curl http://localhost:9411/api/v2/services
```

#### Key Features in Zipkin UI:

- **Dependencies**: View service dependency graph
- **Traces**: Search and analyze specific trace IDs
- **Timeline View**: See timing of each service call
- **Span Details**: View detailed metadata for each span

### 2. Metrics with Micrometer and Prometheus

#### Available Metrics Endpoints

```bash
# All metrics in Prometheus format
curl http://localhost:8080/actuator/prometheus

# Specific metric (replace {metric.name})
curl http://localhost:8080/actuator/metrics/{metric.name}
```

#### Key Metrics Categories:

- **JVM Metrics**: `jvm.*`
  - Memory usage: `jvm.memory.used`
  - Garbage collection: `jvm.gc.*`
  - Thread stats: `jvm.threads.*`
- **HTTP Metrics**: `http.*`
  - Request counts: `http.server.requests`
  - Response times: `http.server.requests.duration`
- **Circuit Breaker Metrics**: `resilience4j.*`
  - State changes: `resilience4j.circuitbreaker.state`
  - Failed calls: `resilience4j.circuitbreaker.calls`

### 3. Spring Boot Actuator Endpoints

#### Core Endpoints

```bash
# List all available endpoints
curl http://localhost:8080/actuator

# Health information
curl http://localhost:8080/actuator/health

# Environment properties
curl http://localhost:8080/actuator/env

# Bean list
curl http://localhost:8080/actuator/beans

# Request mappings
curl http://localhost:8080/actuator/mappings
```

#### Health Indicators

The health endpoint (`/actuator/health`) provides status for:

- Redis connection
- Circuit breakers
- Disk space
- Database connections
- Custom health indicators

### 4. Logging with Trace IDs

#### Example of Tracing a Request:

```bash
# Make a request
curl -v http://localhost:8080/any-endpoint

# Look for these headers in response:
# X-B3-TraceId: Unique ID for the entire request chain
# X-B3-SpanId: ID for this specific service's span
```

#### Log Format

```
2024-03-21 10:15:30.123 INFO [service-name,trace-id,span-id] Message
```

### 5. Monitoring Best Practices

1. **Set Up Grafana Dashboard**

   - Import the provided Grafana dashboard (ID: xxxxx) for:
     - Service health overview
     - Request latency monitoring
     - Circuit breaker states
     - JVM metrics

2. **Alert Configuration**

   - Configure alerts for:
     - High latency (>500ms)
     - Circuit breaker trips
     - Error rate spikes
     - Memory usage >80%

3. **Regular Health Checks**

   ```bash
   # Check overall health
   curl http://localhost:8080/actuator/health

   # Monitor circuit breaker status
   curl http://localhost:8080/actuator/circuitbreakers

   # View recent traces
   curl http://localhost:8080/actuator/traces
   ```

4. **Logging Best Practices**
   - Always include trace ID in logs
   - Use appropriate log levels
   - Implement log rotation
   - Forward logs to centralized logging system

## Prerequisites

- Java 17 or higher
- Redis server (for caching)
- Zipkin server (for distributed tracing)
- Config Server (for centralized configuration)
- Eureka Server (for service discovery)

## Configuration

### Redis Configuration

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

### Circuit Breaker Configuration

```yaml
resilience4j.circuitbreaker:
  instances:
    default:
      registerHealthIndicator: true
      slidingWindowSize: 10
      minimumNumberOfCalls: 5
      permittedNumberOfCallsInHalfOpenState: 3
      waitDurationInOpenState: 5s
      failureRateThreshold: 50
```

### Observability Configuration

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
  tracing:
    sampling:
      probability: 1.0
    zipkin:
      tracing:
        endpoint: http://localhost:9411/api/v2/spans
```

## Getting Started

1. Start the required services:

   ```bash
   # Start Redis
   redis-server

   # Start Zipkin
   java -jar zipkin.jar
   ```

2. Build the service:

   ```bash
   mvn clean install
   ```

3. Run the service:
   ```bash
   mvn spring-boot:run
   ```

## Monitoring

- Actuator endpoints: `http://localhost:8080/actuator`
- Prometheus metrics: `http://localhost:8080/actuator/prometheus`
- Health check: `http://localhost:8080/actuator/health`
- Zipkin UI: `http://localhost:9411`

## Security

The API Gateway implements security through JWT tokens. All endpoints except those in the whitelist require a valid JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Dependencies

- Spring Cloud Gateway
- Spring Boot Actuator
- Spring Data Redis
- Resilience4j
- Micrometer
- Zipkin
- JWT
