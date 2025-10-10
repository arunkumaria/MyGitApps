FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y default-mysql-client && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/demo-azure-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV MYSQL_DB=springdb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=rootpass

CMD bash -c "mysqld_safe & sleep 10 && java -jar app.jar"
