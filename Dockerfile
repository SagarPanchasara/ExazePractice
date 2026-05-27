# Minimal multi-stage Dockerfile for a Maven-built Spring Boot app (Java 8)
# Build stage: use official Maven with OpenJDK 8
FROM maven:3.8.8-openjdk-8 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
# Package application (skip tests to keep build fast)
RUN mvn -DskipTests package -q

# Runtime stage: slim JRE
FROM openjdk:8-jre-slim
ARG JAR=practical-0.0.1-SNAPSHOT.jar
COPY --from=build /src/target/${JAR} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
