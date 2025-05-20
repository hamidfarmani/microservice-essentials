# Comment Service

The Comment Service manages user comments and related operations. It provides REST endpoints for creating, reading, updating, and deleting comments, and integrates with the Notification Service.

## Features

- CRUD operations for comments
- User-specific comment management
- Moderation, pagination, sorting, and search
- User authorization validation

## Configuration

- Runs on port 8081
- Uses PostgreSQL for comment data
- JWT public key for security

## Key API Endpoints

- `GET /api/comments` — List comments (with pagination)
- `GET /api/comments/{id}` — Get comment by ID
- `POST /api/comments` — Create comment
- `PUT /api/comments/{id}` — Update comment
- `DELETE /api/comments/{id}` — Delete comment
- `GET /api/comments/user/{userId}` — Get comments by user
- `POST /api/comments/notification` — Send notification (integrates with Notification Service)

## Security

- JWT validation for protected endpoints
- Authorization checks for modifications
- Rate limiting and input validation

## Database Schema

- Comments table with user and parent references

For more details, see the [root documentation](./root.md).
