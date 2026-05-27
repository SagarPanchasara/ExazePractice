# ─────────────────────────────────────────
# Stage 1: Build
# ─────────────────────────────────────────
FROM maven:3.8.6-openjdk-8 AS builder

WORKDIR /app

# Copy POM first to leverage Docker layer caching for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# ─────────────────────────────────────────
# Stage 2: Runtime
# ─────────────────────────────────────────
FROM openjdk:8-jre-slim

LABEL maintainer="com.example"
LABEL app="practical"
LABEL version="0.0.1-SNAPSHOT"

WORKDIR /app

# Create a non-root user for security
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/practical-0.0.1-SNAPSHOT.jar app.jar

# H2 in-memory DB — no external port needed
# Expose Spring Boot default port
EXPOSE 8080

# JVM tuning for containers
ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0 \
               -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
