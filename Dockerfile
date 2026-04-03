#
# Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
# Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
#

# Use Google's Distroless image for Java 21
# This is a minimal, secure, and production-ready runtime.
FROM gcr.io/distroless/java21-debian12:nonroot

LABEL maintainer="Subhrodip Mohanta <hello@subho.xyz>"
LABEL org.opencontainers.image.source="https://github.com/scaleracademy/twitter-backend-java"

WORKDIR /app

# The JAR file must be built before running the docker build.
# This ensures we use the GitHub Actions Maven cache effectively.
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Standard Spring Boot port
EXPOSE 8080

# Default environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT [ "java", "-jar", "app.jar" ]
