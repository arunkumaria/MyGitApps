# Customer Service

Customer microservice for an AI-powered marketing platform.

## Technology Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Apache Kafka
- Maven
- Docker
- Spring Actuator
- Prometheus

## Run Locally

### 1. Start MySQL

Make sure MySQL is running on:

localhost:3306

Username:

root

Password:

root

### 2. Start Kafka

Kafka should be available on:

localhost:9092

### 3. Start Application

Run:

mvn clean install

Then:

mvn spring-boot:run

Application:

http://localhost:8081

## APIs

POST /api/v1/customers

GET /api/v1/customers

GET /api/v1/customers/{id}

PUT /api/v1/customers/{id}

DELETE /api/v1/customers/{id}

## Actuator

Health:

/actuator/health

Metrics:

/actuator/metrics

Prometheus:

/actuator/prometheus