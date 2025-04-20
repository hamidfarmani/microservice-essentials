# Microservice Essentials

A comprehensive microservices-based application demonstrating essential patterns and best practices for building distributed systems using Spring Boot and Spring Cloud.

## Architecture Overview

The application consists of the following microservices:

1. **API Gateway**: Entry point for all client requests, handles routing and cross-cutting concerns
2. **Auth Service**: Handles authentication and authorization using JWT
3. **Comment Service**: Manages user comments and related operations
4. **Config Server**: Centralized configuration management for all services
5. **Service Discovery**: Service registry and discovery using Spring Cloud Netflix Eureka
6. **Notification Service**: Handles notification management and delivery

### Internal Service Communication

- Services can communicate with each other directly using RestClient without going through the API Gateway
- Internal service-to-service calls (e.g., Comment Service to Notification Service) bypass JWT authentication
- Direct external access to any service endpoints still requires valid JWT authentication through the API Gateway
- This architecture ensures secure external access while maintaining efficient internal communication

## Technology Stack

- Java 21
- Spring Boot 3.4.4
- Spring Cloud
- Maven (Multi-module project)
- JWT for authentication
- Spring Cloud Gateway
- Spring Cloud Config
- Spring Cloud Netflix Eureka

## Prerequisites

- JDK 21
- Maven 3.8+
- Docker (optional, for containerization)

## Project Structure

```
microservice-essentials/
├── api-gateway/         # API Gateway service
├── auth-service/        # Authentication service
├── comment-service/     # Comment management service
├── config-server/       # Centralized configuration
├── notification-service/# Notification management service
├── service-discovery/   # Service discovery (Eureka Server)
└── pom.xml             # Parent POM file
```

## Getting Started

1. Clone the repository:

```bash
git clone <repository-url>
```

2. Build the project:

```bash
mvn clean install
```

3. Start the services in the following order:
   - Service Discovery
   - Config Server
   - Auth Service
   - Comment Service
   - API Gateway

Each service can be started using:

```bash
cd <service-directory>
mvn spring-boot:run
```

## Service Ports

- Service Discovery: 8761
- Config Server: 8888
- Auth Service: 9898
- Comment Service: 8081
- Notification Service: 8082
- API Gateway: 8080

## Documentation

Each microservice has its own README with detailed documentation about its specific functionality, API endpoints, and configuration options.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Register a user

```
curl --location --request POST 'http://localhost:8080/auth/register' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=7CE91EE75A65277C0DCB6C5736C5DF5D' \
--data-raw '{
    "name":"Basant",
    "password":"Pwd1",
    "email":"basant@gmail.com"
}'

```

## Generate token

```
curl --location --request POST 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=7CE91EE75A65277C0DCB6C5736C5DF5D' \
--data-raw '{
    "username":"Basant",
    "password":"Pwd1"
}'
```

## Access Comment-service

```
curl --location --request GET 'http://localhost:8080/comment' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCYXNhbnQiLCJpYXQiOjE2NzkwNTU4MDIsImV4cCI6MTY3OTA1NzYwMn0.Q0bwS5_16q1Z8K-p_flpmyRoJNFCyOhU2AMKSNYh66o' \
--header 'Cookie: JSESSIONID=7CE91EE75A65277C0DCB6C5736C5DF5D'
```

## API Endpoints

All requests go through the API Gateway running on port 8080.

### Auth Service

#### Register a new user

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### Validate Token

```bash
curl -X GET "http://localhost:8080/auth/validate?token=your-token-here"
```

### Comment Service

#### Get All Comments

```bash
curl -X GET http://localhost:8080/api/comments \
  -H "Authorization: Bearer your-token-here"
```

#### Get Comments by Post ID

```bash
curl -X GET http://localhost:8080/api/comments/post/1 \
  -H "Authorization: Bearer your-token-here"
```

#### Get Comment by ID

```bash
curl -X GET http://localhost:8080/api/comments/1 \
  -H "Authorization: Bearer your-token-here"
```

#### Send Notification through Comment Service

```bash
curl -X POST http://localhost:8080/api/comments/notification \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer your-token-here" \
  -d '"This is a test notification message"'
```

### Notification Service

#### Send Notification

```bash
curl -X POST http://localhost:8080/api/notifications/send \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer your-token-here" \
  -d '"This is a test notification"'
```

#### Get Notification

```bash
curl -X GET http://localhost:8080/api/notifications \
  -H "Authorization: Bearer your-token-here"
```

## Usage Flow

1. First, register a user using the register endpoint
2. Login with the registered user credentials to get a JWT token
3. Use the received token in the `Authorization` header for all other requests
4. The token can be validated using the validate endpoint

## Observability & Monitoring

This project implements comprehensive observability using various tools and practices:

### 1. Distributed Tracing

- **Tool**: Zipkin (http://localhost:9411)
- Tracks requests across all microservices
- Visualizes service dependencies and latencies
- Helps debug and optimize service interactions

### 2. Metrics & Monitoring

- **Tools**: Micrometer, Prometheus, Spring Boot Actuator
- Access metrics: http://localhost:8080/actuator/prometheus
- Key metrics available:
  - Service health and availability
  - Request latencies and counts
  - Circuit breaker states
  - JVM and system metrics

### 3. Health Checks

Each service exposes health endpoints:

```bash
# API Gateway
curl http://localhost:8080/actuator/health

# Auth Service
curl http://localhost:9898/actuator/health

# Comment Service
curl http://localhost:8081/actuator/health

# Notification Service
curl http://localhost:8082/actuator/health
```

### 4. Logging

- Distributed tracing IDs included in logs
- Consistent log format across services
- Centralized logging configuration

### Quick Observability Check

```bash
# 1. Check if services are registered
curl http://localhost:8761/eureka/apps

# 2. View API Gateway health
curl http://localhost:8080/actuator/health

# 3. Check Zipkin
curl http://localhost:9411/api/v2/services

# 4. View Prometheus metrics
curl http://localhost:8080/actuator/prometheus
```

For detailed information about observability features and setup, refer to the API Gateway's README.

## Notes

- All requests (except auth endpoints) require a valid JWT token in the `Authorization` header
- The token should be included in the format: `Bearer your-token-here`
- The API Gateway handles routing to the appropriate microservice
- All services are accessed through the API Gateway on port 8080
