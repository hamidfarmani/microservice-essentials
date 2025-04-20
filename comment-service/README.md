# Comment Service

The Comment Service manages user comments and related operations in the microservices architecture. It provides REST endpoints for creating, reading, updating, and deleting comments.

## Features

- CRUD operations for comments
- User-specific comment management
- Comment moderation capabilities
- Pagination and sorting
- Comment search functionality
- User authorization validation

## Configuration

The service runs on port 8081 by default and can be configured through the following properties:

```yaml
server:
  port: 8081

spring:
  application:
    name: comment-service
  datasource:
    url: jdbc:postgresql://localhost:5432/comment_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update

security:
  jwt:
    public-key: ${JWT_PUBLIC_KEY}
```

## API Endpoints

All endpoints require JWT authentication except where noted.

### Comment Management

#### Get All Comments

```http
GET /api/comments?page=0&size=10&sort=createdAt,desc
```

#### Get Comment by ID

```http
GET /api/comments/{id}
```

#### Create Comment

```http
POST /api/comments
Content-Type: application/json

{
  "content": "string",
  "parentId": "string" (optional)
}
```

#### Update Comment

```http
PUT /api/comments/{id}
Content-Type: application/json

{
  "content": "string"
}
```

#### Delete Comment

```http
DELETE /api/comments/{id}
```

#### Get User's Comments

```http
GET /api/comments/user/{userId}?page=0&size=10
```

## Service Integration

### Notification Service Integration

The Comment Service integrates with the Notification Service for sending notifications. This integration uses direct service-to-service communication via RestClient, bypassing the API Gateway.

#### Send Notification

```http
POST /api/comments/notification
Content-Type: application/json

"Your notification message"
```

Note: While internal service calls (Comment Service → Notification Service) bypass JWT authentication, external access to this endpoint through the API Gateway still requires valid JWT authentication.

## Security

- JWT validation for all protected endpoints
- User authorization checks for comment modifications
- Rate limiting per user
- Input validation and sanitization
- XSS protection

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Spring Cloud Config Client
- Spring Cloud Netflix Eureka Client

## Database Schema

```sql
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT REFERENCES comments(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_comments_user_id ON comments(user_id);
CREATE INDEX idx_comments_parent_id ON comments(parent_id);
```

## Error Handling

The service provides detailed error responses in the following format:

```json
{
  "timestamp": "2024-03-21T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Detailed error message",
  "path": "/api/comments"
}
```

## Monitoring

The service exposes actuator endpoints for monitoring:

- Health check: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`
