FROM eclipse-temurin:17

WORKDIR /app

COPY target/order-service.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]