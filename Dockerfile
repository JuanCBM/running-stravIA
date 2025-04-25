FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the JAR file
COPY bootstrap/target/bootstrap-0.0.1-SNAPSHOT.jar app.jar

# Set production environment variables
# This explicitly tells Spring Boot to use application-prod.properties
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Strava API configuration via environment variables
# These will be provided at runtime, but we define them here for documentation
ENV STRAVA_CLIENT_ID=""
ENV STRAVA_CLIENT_SECRET=""
ENV STRAVA_REDIRECT_URI=""
ENV STRAVA_FRONTEND_URL=""

# Production-specific settings
ENV LOGGING_LEVEL_COM_WIKIBLAZ_STRAVIARUNNING=INFO
ENV LOGGING_LEVEL_ORG_HIBERNATE_SQL=INFO
ENV LOGGING_LEVEL_ORG_HIBERNATE_TYPE_DESCRIPTOR_SQL_BASICBINDER=INFO
ENV SPRING_JPA_SHOW_SQL=false
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=false

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
