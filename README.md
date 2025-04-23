# Running StravIA

![Running StravIA Logo](logo.png)

A Spring Boot application for tracking running activities, built using Hexagonal Architecture (also known as Ports and Adapters).

## Architecture Overview

This project follows the Hexagonal Architecture pattern, which emphasizes separation of concerns and dependency inversion. The core business logic is isolated from external concerns like databases and UI.

### Hexagonal Architecture Diagram

```
                     ┌─────────────────────────────────────────────┐
                     │                                             │
                     │                  BOOTSTRAP                  │
                     │                                             │
                     └─────────────────────────────────────────────┘
                                        │
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                                                                                 │
│  ┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐    │
│  │                 │         │                 │         │                 │    │
│  │  REST ADAPTER   │         │   APPLICATION   │         │  PERSISTENCE    │    │
│  │  (Controller)   │◄───────►│   (Use Cases)   │◄───────►│  ADAPTER        │    │
│  │                 │         │                 │         │                 │    │
│  └────────┬────────┘         └────────┬────────┘         └────────┬────────┘    │
│           │                           │                           │             │
│           │                           │                           │             │
│           ▼                           ▼                           ▼             │
│  ┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐    │
│  │                 │         │                 │         │                 │    │
│  │  REST MODEL     │         │  DOMAIN MODEL   │         │  PERSISTENCE    │    │
│  │  (DTOs)         │         │  (Entities)     │         │  MODEL          │    │
│  │                 │         │                 │         │  (Entities)     │    │
│  └─────────────────┘         └─────────────────┘         └─────────────────┘    │
│                                                                                 │
└─────────────────────────────────────────────────────────────────────────────────┘
```

### Key Principles

1. **Domain-Centric**: The domain model is at the center of the design
2. **Dependency Inversion**: Dependencies point inward, with the domain defining interfaces (ports) that are implemented by adapters
3. **Isolation**: The domain is isolated from external concerns like databases and UI
4. **Testability**: Components can be tested in isolation with mocks for the ports

### Hexagonal Architecture Layers

The hexagonal architecture divides the application into three main layers:

#### Domain Layer

The domain layer is the innermost layer and represents the core of the application:

- Contains business entities, value objects, and domain services
- Defines business rules and logic
- Has no dependencies on other layers or external frameworks
- Includes interfaces (ports) that define how the domain interacts with the outside world
- In this project: `Activity` and `ActivityType` entities, `ActivityRepository` interface

#### Application Layer

The application layer surrounds the domain layer and orchestrates the use cases:

- Implements use cases by coordinating domain objects
- Contains application services that implement business workflows
- Depends only on the domain layer
- Defines input ports (interfaces for incoming requests) and uses output ports (interfaces for outgoing requests)
- In this project: `ActivityUseCase` interface and `ActivityService` implementation

#### Infrastructure Layer

The infrastructure layer is the outermost layer and connects the application to external systems:

- Contains adapters that implement the interfaces defined by the domain
- Handles technical concerns like persistence, UI, external APIs, etc.
- Depends on the domain and application layers
- Adapts external technologies to the needs of the application
- In this project: REST controllers, JPA repositories, and their respective adapters

The flow of dependencies in hexagonal architecture is from the outside in:
- Infrastructure → Application → Domain

The flow of control can go in either direction, using interfaces (ports) to cross boundaries.

## Project Structure

The project is organized into several modules, each with a specific responsibility:

### Domain Module

The core of the application containing business entities and repository interfaces (ports).

- **Models**: `Activity`, `ActivityType`
- **Ports**: `ActivityRepository`

### Application Module

Contains the application services that implement use cases by orchestrating domain objects.

- **Ports**: `ActivityUseCase` (input port)
- **Services**: `ActivityService`

### Persistence Module

Implements the persistence adapter for storing and retrieving domain entities.

- **Entities**: `ActivityEntity`
- **Repositories**: `ActivityJpaRepository`
- **Adapters**: `ActivityRepositoryAdapter`
- **Mappers**: `ActivityMapper`

### REST Module

Implements the REST API adapter for exposing the application functionality.

- **Controllers**: `ActivityController`
- **DTOs**: `Activity`, `ActivityRequest`
- **Mappers**: `ActivityMapper`

### Bootstrap Module

The entry point of the application that wires all components together.

- **Main Application**: `StraviaRunningApplication`

## Features

- Create, read, update, and delete running activities
- Track activity details like distance, duration, elevation gain
- Support for different activity types (Run, Trail Run, Track Run, etc.)

## Technology Stack

- **Java 21+**
- **Spring Boot**: Application framework
- **Spring Data JPA**: Data access
- **Lombok**: Reduces boilerplate code
- **MapStruct**: Object mapping
- **OpenAPI**: API specification and code generation

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven

### Running the Application

1. Clone the repository
2. Navigate to the project root
3. Run `mvn clean install`
4. Run `mvn spring-boot:run -pl bootstrap`

The application will start on http://localhost:8080

## API Documentation

The API is documented using OpenAPI. Once the application is running, you can access the API documentation at:

http://localhost:8080/swagger-ui.html
