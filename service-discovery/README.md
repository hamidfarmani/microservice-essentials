# Service Discovery

The Service Discovery module implements Netflix Eureka Server for service registration and discovery in the microservices architecture. It enables automatic detection of service instances and load balancing.

## Features

- Service registration and discovery
- Health monitoring
- Load balancing support
- High availability configuration
- Service status dashboard
- REST API for service registry

## Configuration

The service runs on port 8761 by default and can be configured through the following properties:

```yaml
server:
  port: 8761

spring:
  application:
    name: service-discovery
  security:
    user:
      name: ${EUREKA_USERNAME}
      password: ${EUREKA_PASSWORD}

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
  server:
    enable-self-preservation: false
    renewal-percent-threshold: 0.85
```

## High Availability Setup

For production environments, it's recommended to run multiple instances of Eureka Server. Here's an example configuration for a two-node setup:

### Instance 1

```yaml
spring:
  profiles: peer1
eureka:
  instance:
    hostname: peer1
  client:
    serviceUrl:
      defaultZone: http://peer2:8762/eureka/
```

### Instance 2

```yaml
spring:
  profiles: peer2
server:
  port: 8762
eureka:
  instance:
    hostname: peer2
  client:
    serviceUrl:
      defaultZone: http://peer1:8761/eureka/
```

## Client Configuration

Clients should include the following configuration to register with Eureka:

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://${EUREKA_USERNAME}:${EUREKA_PASSWORD}@localhost:8761/eureka/
  instance:
    preferIpAddress: true
```

## Security

- Basic authentication for the dashboard and REST endpoints
- SSL/TLS support for secure communication
- IP address-based security (optional)

## Dependencies

- Spring Cloud Netflix Eureka Server
- Spring Security
- Spring Boot Actuator

## REST API Endpoints

### Service Operations

```http
# Get all instances
GET /eureka/apps
Accept: application/json

# Get specific application
GET /eureka/apps/{appId}

# Get specific instance
GET /eureka/apps/{appId}/{instanceId}

# Register new instance
POST /eureka/apps/{appId}

# De-register instance
DELETE /eureka/apps/{appId}/{instanceId}

# Send heartbeat
PUT /eureka/apps/{appId}/{instanceId}
```

## Dashboard

The Eureka dashboard is available at:

```
http://localhost:8761
```

Features:

- System Status
- Instance Info
- General Info
- Instance Health
- Availability Zones

## Monitoring

The service exposes various actuator endpoints:

- Health check: `/actuator/health`
- Metrics: `/actuator/metrics`
- Environment: `/actuator/env`

## Environment Variables

| Variable        | Description             | Required |
| --------------- | ----------------------- | -------- |
| EUREKA_USERNAME | Username for basic auth | Yes      |
| EUREKA_PASSWORD | Password for basic auth | Yes      |

## Best Practices

1. Run multiple instances in production
2. Enable self-preservation in production
3. Configure appropriate timeouts
4. Use secure communication
5. Monitor instance health
6. Regular backup of registry
