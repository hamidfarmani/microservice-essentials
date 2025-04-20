# Notification Service

The Notification Service handles notification management and delivery in the microservices architecture. It provides REST endpoints for sending and retrieving notifications.

## Features

- Send notifications
- Retrieve notification status
- Integration with other services
- Scalable notification handling

## API Endpoints

### Send Notification

```http
POST /api/notifications/send
Content-Type: application/json

"Your notification message"
```

Response:

```json
{
  "message": "Notification with message: Your notification message sent successfully!",
  "status": "SUCCESS"
}
```

### Get Notification Status

```http
GET /api/notifications
```

Response: Returns a string message confirming the service is operational.

## Service Integration

### Internal Service Communication

- The service can be called directly by other microservices (e.g., Comment Service) using RestClient
- Internal service-to-service calls bypass the API Gateway and JWT authentication
- Direct external access through the API Gateway requires JWT authentication
- This design ensures secure external access while maintaining efficient internal communication

## Dependencies

- Spring Boot
- Spring Cloud Netflix Eureka Client
- Spring Cloud Config Client

## Error Handling

The service provides detailed error responses in the following format:

```json
{
  "timestamp": "2024-03-21T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Detailed error message",
  "path": "/api/notifications/send"
}
```

## Monitoring

The service exposes actuator endpoints for monitoring:

- Health check: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`
