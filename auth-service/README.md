# Authentication Service

The Authentication Service handles user authentication and authorization using JWT tokens. It provides endpoints for user registration, login, and token management.

## Features

- User registration and login
- JWT token generation and validation
- Token refresh mechanism
- Role-based access control
- Password encryption using BCrypt
- User profile management

## Configuration

The service runs on port 9898 by default and can be configured through the following properties:

```yaml
server:
  port: 9898

spring:
  application:
    name: auth-service
  datasource:
    url: jdbc:postgresql://localhost:5432/auth_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: ${JWT_SECRET}
  expiration: 3600000 # 1 hour in milliseconds
  refresh-token:
    expiration: 86400000 # 24 hours in milliseconds
```

## API Endpoints

### Public Endpoints

#### User Registration

```http
POST /auth/register
Content-Type: application/json

{
  "username": "string",
  "email": "string",
  "password": "string"
}
```

#### User Login

```http
POST /auth/login
Content-Type: application/json

{
  "username": "string",
  "password": "string"
}
```

### Protected Endpoints

#### Refresh Token

```http
POST /auth/refresh-token
Authorization: Bearer <refresh_token>
```

#### Get User Profile

```http
GET /auth/profile
Authorization: Bearer <access_token>
```

#### Update User Profile

```http
PUT /auth/profile
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "email": "string",
  "currentPassword": "string",
  "newPassword": "string"
}
```

## Security

- Passwords are hashed using BCrypt
- JWT tokens are signed with a secure secret key
- Access tokens expire after 1 hour
- Refresh tokens expire after 24 hours
- Rate limiting on authentication endpoints
- CORS configuration for allowed origins

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JJWT (JSON Web Token)
- Spring Cloud Config Client
- Spring Cloud Netflix Eureka Client

## Database Schema

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id),
    role_id BIGINT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);
```
