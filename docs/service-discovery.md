# Service Discovery

The Service Discovery module implements Netflix Eureka Server for service registration and discovery. It enables automatic detection of service instances and load balancing.

## Features

- Service registration and discovery
- Health monitoring
- Load balancing support
- High availability configuration
- Service status dashboard
- REST API for service registry

## Configuration

- Runs on port 8761
- Basic authentication for dashboard and REST endpoints
- SSL/TLS support

## Key API Endpoints

- `GET /eureka/apps` — Get all registered instances
- `GET /eureka/apps/{appId}` — Get specific application
- `GET /eureka/apps/{appId}/{instanceId}` — Get specific instance
- `POST /eureka/apps/{appId}` — Register new instance
- `DELETE /eureka/apps/{appId}/{instanceId}` — De-register instance
- `PUT /eureka/apps/{appId}/{instanceId}` — Send heartbeat

## Dashboard

- Available at `http://localhost:8761`
- Shows system status, instance info, and health

## Monitoring

- Exposes actuator endpoints for health, metrics, and environment

For more details, see the [root documentation](./root.md).
