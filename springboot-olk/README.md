# springboot-olk

## Overview

**springboot-olk** is a simple RESTful web application for tracking manga reading progress. Built with **Java 21** and **Spring Boot**, it demonstrates modern Java features, exposes health and metrics endpoints, and integrates with **Prometheus** for monitoring and **Loki (OLK stack)** for centralized logging.

## Features

- **REST API** to add and list manga entries (name and chapter).
- **In-memory storage** for manga entries.
- **Metrics** exposed via `/actuator/metrics` and `/actuator/prometheus` for Prometheus scraping.
- **Structured logging** sent to both the console and a Loki instance for observability.
- **Java 21** language features and records.

## Technology Stack

- **Java 21**
- **Spring Boot 3.5**
- **Micrometer** (Prometheus metrics)
- **Loki4j** (Logback appender for Loki)
- **Gradle** (Kotlin DSL)

## API Endpoints

- `POST /api/manga/add`  
  Add a manga entry.  
  **Body:**
  ```json
  {
    "name": "One Piece",
    "chapter": 1100
  }
  ```
- `GET /api/manga/list`  
  List all manga entries.

## Observability

- **Metrics:**
  - Prometheus metrics available at `/actuator/prometheus`.
  - Custom counter `manga_add_total` increments on each manga addition.
- **Logging:**
  - Console logs for local debugging.
  - Logs are also sent to a Loki instance (see `src/main/resources/logback-spring.xml`).

## Configuration

- **Application properties:**  
  See [`src/main/resources/application.properties`](src/main/resources/application.properties) for server port and actuator settings.
- **Loki integration:**  
  Configured in [`src/main/resources/logback-spring.xml`](src/main/resources/logback-spring.xml).  
  Update the `<url>` if your Loki instance is not running on `localhost:3100`.

## Running the App

1. **Build:**
   ```sh
   ./gradlew build
   ```
2. **Run:**
   ```sh
   ./gradlew bootRun
   ```
3. **Test API:**  
   Use `curl` or Postman to interact with the endpoints.

## Requirements

- Java 21+
- Loki and Prometheus (optional, for full observability stack)

---

**OLK** stands for **OpenTelemetry, Loki, and Kubernetes**. In this project, Loki is used for log aggregation, making it easy to collect and analyze logs from your Spring Boot
