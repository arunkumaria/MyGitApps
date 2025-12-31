## Order Service Full Documentation

This document outlines the step-by-step implementation of the Order Service application, including its creation, Dockerization, Kubernetes deployment, and multi-microservice split.

---

### 1. Order Service Creation

**Technologies Used:**

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* Kafka
* Docker
* Kubernetes

**Key Functionalities:**

* User authentication (JWT-based)
* REST APIs for managing orders
* Kafka events on order creation
* Persistence in MySQL

**Project Structure:**

```
order-service/
├── src/main/java/com/example/orderservice
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   └── security
├── src/main/resources
│   └── application.yml
├── Dockerfile
└── pom.xml
```

**Endpoints:**

* `/api/auth/login` - JWT authentication
* `/api/orders` - CRUD operations for orders

---

### 2. Dockerization

**Dockerfile:**

```dockerfile
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/order-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Build Docker Image:**

```bash
docker build -t order-service:latest .
```

**Verify Local Image:**

```bash
docker images | grep order-service
```

---

### 3. Kubernetes Deployment (Single Service)

**Namespace:**

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: order-system
```

**Deployment + NodePort Service:**

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service
  namespace: order-system
spec:
  replicas: 2
  selector:
    matchLabels:
      app: order-service
  template:
    metadata:
      labels:
        app: order-service
    spec:
      containers:
        - name: order-service
          image: order-service:latest
          imagePullPolicy: Never
          ports:
            - containerPort: 8080
          env:
            - name: SPRING_DATASOURCE_URL
              value: jdbc:mysql://mysql:3306/orderdb
            - name: SPRING_DATASOURCE_USERNAME
              value: root
            - name: SPRING_DATASOURCE_PASSWORD
              value: dinavella5
            - name: SPRING_KAFKA_BOOTSTRAP_SERVERS
              value: kafka:9092
---
apiVersion: v1
kind: Service
metadata:
  name: order-service
  namespace: order-system
spec:
  type: NodePort
  selector:
    app: order-service
  ports:
    - port: 8080
      targetPort: 8080
      nodePort: 31069
```

**Apply Deployment:**

```bash
kubectl apply -f order-service.yaml
kubectl get pods -n order-system
```

**Test API:**

```
http://localhost:31069/api/orders
```

Expected response: `401 Unauthorized` (JWT authentication required)

---

### 4. Multi-Microservice Split for Order Service

**Microservices:**

1. **Order API Service** - Handles REST endpoints.
2. **Order Processing Service** - Handles business logic and Kafka events.
3. **MySQL DB Service** - Persists order data.
4. **Kafka + Zookeeper** - Event streaming platform.

**Kubernetes Split Example:**

* `order-api.yaml` → Exposes NodePort 31069
* `order-processing.yaml` → Runs independently, consumes Kafka messages
* `mysql.yaml` → StatefulSet for DB
* `kafka.yaml` / `zookeeper.yaml` → StatefulSets for event streaming

**Benefits of Split:**

* Independent scaling
* Fault tolerance
* Loose coupling
* Clear separation of concerns

**Test Flow:**

1. Login: `POST /api/auth/login`
2. Use JWT to call `POST /api/orders`
3. Kafka event is consumed by Order Processing service
4. Data persisted in MySQL

---

### 5. Key Kubernetes Notes

* **imagePullPolicy: Never** is required for local images in Docker Desktop Kubernetes.
* Readiness/Liveness probes must match actual exposed actuator endpoints.
* NodePort allows testing APIs via `http://localhost:<nodePort>`.
* Ensure services and deployments use correct labels for proper routing.

---

### 6. Next Steps (Production Ready)

* Enable readiness/liveness probes correctly.
* Secure actuator endpoints.
* Use StatefulSets for Order API and Order Processing for high availability.
* Add Helm charts for easier deployments.
* Add CI/CD pipelines for automated build and deployment.

---

**Documentation Author:** Arun Kumar I A
**Project Date:** 2025-12-31
