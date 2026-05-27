# Minimal multi-stage Dockerfile for Maven-built Spring Boot app (Java 8) compatible with podman
FROM maven:3.8.8-jdk-8 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
# Use batch mode and skip tests for non-interactive builds
RUN mvn -B -DskipTests package -q

FROM openjdk:8-jre-slim
WORKDIR /app
# Copy any produced jar from the build stage (supports varying jar names)
COPY --from=build /src/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
