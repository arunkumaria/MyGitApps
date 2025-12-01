FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/paypal-payment-app.jar app.jar
EXPOSE 8082
CMD ["sh", "-c", "sleep 10 && java -jar app.jar"]
