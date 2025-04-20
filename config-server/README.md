# Config Server

The Config Server provides centralized configuration management for all microservices in the architecture. It uses Spring Cloud Config Server to serve configuration properties from a Git repository.

## Features

- Centralized configuration management
- Git-backed configuration storage
- Environment-specific configurations
- Configuration encryption/decryption
- Dynamic configuration updates
- Configuration versioning

## Configuration

The service runs on port 8888 by default and can be configured through the following properties:

```yaml
server:
  port: 8888

spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: ${CONFIG_GIT_URI}
          default-label: main
          search-paths:
            - "*service"
          username: ${CONFIG_GIT_USERNAME}
          password: ${CONFIG_GIT_PASSWORD}
  security:
    user:
      name: ${CONFIG_SERVER_USERNAME}
      password: ${CONFIG_SERVER_PASSWORD}
```

## Git Repository Structure

The configuration files in the Git repository should follow this structure:

```
config-repo/
├── application.yml           # Common configurations
├── application-dev.yml       # Development environment
├── application-prod.yml      # Production environment
├── auth-service.yml         # Auth service specific
├── comment-service.yml      # Comment service specific
└── api-gateway.yml         # API Gateway specific
```

## API Endpoints

### Get Configuration

```http
GET /{application-name}/{profile}[/{label}]
Authorization: Basic <credentials>
```

Example:

```http
GET /auth-service/dev/main
```

### Encrypt Value

```http
POST /encrypt
Authorization: Basic <credentials>
Content-Type: text/plain

value-to-encrypt
```

### Decrypt Value

```http
POST /decrypt
Authorization: Basic <credentials>
Content-Type: text/plain

encrypted-value
```

## Security

- Basic authentication required for all endpoints
- Encryption key for sensitive properties
- HTTPS required in production
- IP whitelisting (optional)

## Dependencies

- Spring Cloud Config Server
- Spring Security
- Spring Cloud Netflix Eureka Client

## Environment Variables

| Variable               | Description                   | Required |
| ---------------------- | ----------------------------- | -------- |
| CONFIG_GIT_URI         | Git repository URL            | Yes      |
| CONFIG_GIT_USERNAME    | Git repository username       | No       |
| CONFIG_GIT_PASSWORD    | Git repository password/token | No       |
| CONFIG_SERVER_USERNAME | Config server username        | Yes      |
| CONFIG_SERVER_PASSWORD | Config server password        | Yes      |
| ENCRYPT_KEY            | Key for property encryption   | Yes      |

## Client Configuration

Clients should include the following configuration to connect to the Config Server:

```yaml
spring:
  config:
    import: "optional:configserver:http://localhost:8888"
  cloud:
    config:
      username: ${CONFIG_SERVER_USERNAME}
      password: ${CONFIG_SERVER_PASSWORD}
      fail-fast: true
      retry:
        max-attempts: 6
        initial-interval: 1000
        max-interval: 2000
        multiplier: 1.1
```

## Monitoring

The service exposes actuator endpoints for monitoring:

- Health check: `/actuator/health`
- Environment: `/actuator/env`
- Metrics: `/actuator/metrics`
