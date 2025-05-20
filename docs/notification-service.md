# Notification Service

The Notification Service handles notification management and delivery. It provides REST endpoints for sending and retrieving notifications, and integrates with other services for internal communication.

## Features

- Send notifications
- Retrieve notification status
- Integration with other services (e.g., Comment Service)
- Scalable notification handling

## Key API Endpoints

- `POST /api/notifications/send` — Send a notification
- `GET /api/notifications` — Get notification status

## Service Integration

- Can be called directly by other microservices (bypassing API Gateway and JWT)
- External access requires JWT authentication

## Monitoring

- Exposes actuator endpoints for health, metrics, and info

For more details, see the [root documentation](./root.md).
