# Auth Service

The Auth Service handles user authentication and authorization using JWT tokens. It provides endpoints for user registration, login, token management, and user profile operations.

## Features

- User registration and login
- JWT token generation and validation
- Token refresh mechanism
- Role-based access control
- Password encryption (BCrypt)
- User profile management

## Configuration

- Runs on port 9898
- Uses PostgreSQL for user data
- JWT secret and expiration settings

## Key API Endpoints

- `POST /auth/register` — Register a new user
- `POST /auth/login` — User login
- `POST /auth/refresh-token` — Refresh JWT token
- `GET /auth/profile` — Get user profile
- `PUT /auth/profile` — Update user profile

## Security

- Passwords hashed with BCrypt
- JWT tokens signed with a secure secret
- Access/refresh token expiration
- Rate limiting and CORS configuration

## Database Schema

- Users, roles, and user_roles tables

For more details, see the [root documentation](./root.md).
