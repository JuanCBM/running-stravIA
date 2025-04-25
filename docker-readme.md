# Docker Deployment Guide for Running-StravIA

This guide explains how to build and deploy the Running-StravIA application using Docker with production configuration.

## Prerequisites

- Docker installed on your system
- Java 21 and Maven installed (for building the application)

## Building the Application

Before creating the Docker image, you need to build the application:

```bash
mvn clean package
```

This will create the executable JAR file in the `bootstrap/target` directory.

## Building the Docker Image

To build the Docker image:

```bash
docker build -t running-stravia:latest .
```

## Running the Docker Container

To run the application in a Docker container:

```bash
docker run -p 8080:8080 \
  -e STRAVA_CLIENT_ID=your_client_id \
  -e STRAVA_CLIENT_SECRET=your_client_secret \
  -e STRAVA_REDIRECT_URI=your_redirect_uri \
  -e STRAVA_FRONTEND_URL=your_frontend_url \
  running-stravia:latest
```

Replace the placeholder values with your actual Strava API credentials.

## Environment Variables

The following environment variables can be configured:

| Variable | Description | Required |
|----------|-------------|----------|
| STRAVA_CLIENT_ID | Your Strava API client ID | Yes |
| STRAVA_CLIENT_SECRET | Your Strava API client secret | Yes |
| STRAVA_REDIRECT_URI | The URI Strava will redirect to after authentication | Yes |
| STRAVA_FRONTEND_URL | Your frontend application URL | Yes |
| SPRING_PROFILES_ACTIVE | Spring profile to activate (default: "prod") | No |
| JAVA_OPTS | JVM options (default: "-Xms512m -Xmx1024m") | No |

## Production Configuration

The Docker image is configured to run with the `prod` Spring profile by setting the `SPRING_PROFILES_ACTIVE=prod` environment variable in the Dockerfile. This explicitly tells Spring Boot to use the `application-prod.properties` file for configuration.

The production configuration includes:

- Reduced logging verbosity (INFO level)
- Disabled SQL query logging
- Disabled H2 console for security
- Memory optimization for the JVM

## Persistent Storage

By default, the application uses an in-memory H2 database. For a true production environment, you might want to:

1. Configure a persistent database like PostgreSQL or MySQL
2. Mount a volume for data persistence
3. Set up database connection properties via environment variables

## Health Checks

You can check if the application is running by accessing:

```
http://your-docker-host:8080/actuator/health
```

(Note: This requires Spring Boot Actuator to be enabled in the application)
