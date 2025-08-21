# Stage 1: Build
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

# Copy Gradle wrapper and build scripts
COPY gradlew ./
COPY gradle/ gradle/
COPY build.gradle.kts settings.gradle.kts ./

# Make wrapper executable
RUN chmod +x ./gradlew

# Build dependencies only (useful for caching)
RUN ./gradlew -q dependencies || true

# Copy source and build
COPY src src/
RUN ./gradlew clean build -x test

# Stage 2: Runtime
FROM eclipse-temurin:23-jre AS runtime
WORKDIR /opt/app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["sh", "-c", "java -XX:+UseZGC -XX:MaxRAMPercentage=75 -jar app.jar"]
