# Config Server

The Config Server provides centralized configuration management for all microservices. It serves configuration properties from a Git repository and supports encryption, versioning, and dynamic updates.

## Features

- Centralized configuration management
- Git-backed storage
- Environment-specific configs
- Encryption/decryption support
- Dynamic updates and versioning

## Configuration

- Runs on port 8888
- Connects to a Git repository for configs
- Basic authentication for endpoints

## Key API Endpoints

- `GET /{application-name}/{profile}[/{label}]` — Get configuration
- `POST /encrypt` — Encrypt a value
- `POST /decrypt` — Decrypt a value

## Security

- Basic authentication required
- Encryption key for sensitive properties
- HTTPS recommended in production

## Monitoring

- Exposes actuator endpoints for health, environment, and metrics

For more details, see the [root documentation](./root.md).
