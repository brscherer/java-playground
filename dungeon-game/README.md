# Dungeon Game REST API

A **Spring Boot 3.5.4** REST API that solves the [Dungeon Game problem](https://leetcode.com/problems/dungeon-game/), supports **A/B testing experiments**, and persists results in **PostgreSQL 17**. The application is fully containerized with **Docker** and uses **Flyway** for database migrations.

---

## Features

- Solve the Dungeon Game problem using **dynamic programming (1D & 2D approaches)**.
- REST API for solving dungeons and returning minimum health required.
- **A/B testing** with configurable experiment splits.
- Persistent storage with **PostgreSQL**:
    - Dungeon solutions
    - Experiment exposures
- Flyway-based migrations for database version control.
- Fully containerized with Docker + Docker Compose.
- Configurable via `application.yaml`.

---

## Tech Stack

- Java 23
- Spring Boot 3.5.4
- PostgreSQL 17
- Hibernate / JPA
- Flyway for migrations
- Docker & Docker Compose
- Gradle build system

---

## Prerequisites

- Docker 23+
- Docker Compose 2+
- Java 23 (for local dev, optional if using Docker)
- Gradle 8+ (optional, Docker builds include Gradle wrapper)

---

## Setup and Run with Docker Compose

1. **Clone the repository**

```bash
git clone <repository_url>
cd dungeon-app
```

2. **Build and start containers**

```bash
docker compose up --build
```

* PostgreSQL 17 container starts.
* Flyway automatically applies migrations.
* Dungeon Game REST API starts on localhost:8080

## Test the API
```
curl -X POST -H "Content-Type: application/json" \
     -d '{"playerId":"player1","dungeon":[[0,-3],[-10,0]]}' \
     http://localhost:8080/api/dungeon/solve
```

Expected response:
````json
{
  "playerId": "player1",
  "minHealthRequired": 7,
  "variant": "A"
}
````

