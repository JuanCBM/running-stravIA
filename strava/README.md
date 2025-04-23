# Strava Integration Module

This module provides integration with the Strava API for the running-stravIA application.

## Structure

The module follows the hexagonal architecture pattern and contains:

- `adapter`: Contains the adapter for the Strava API
- `config`: Contains configuration for the Strava API
- `mapper`: Contains mappers for converting between Strava API objects and domain models

## Configuration

To use this module, you need to configure the following properties in `application.properties`:

```properties
# Strava API Configuration
strava.client.id=your_client_id
strava.client.secret=your_client_secret
strava.redirect.uri=http://localhost:8080/strava/callback
```

You can obtain these values by registering your application with Strava at https://www.strava.com/settings/api

## Implementation

To implement the Strava integration:

1. Update the `StravaAdapter` class to implement the methods for interacting with the Strava API
2. Update the `StravaConfig` class to configure the Strava API client
3. Update the `StravaMapper` interface to map between Strava API objects and domain models

## Dependencies

This module depends on:
- The application module for access to domain models and ports
- The javastrava-api library for Strava API access
- Spring for dependency injection and configuration
- MapStruct for object mapping