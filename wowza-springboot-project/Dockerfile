FROM maven:3.9-eclipse-temurin-11 AS builder

WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download Wowza JAR and install to local Maven repo
RUN mkdir -p /wowza-libs
COPY wms-server.jar /wowza-libs/ 2>/dev/null || echo "Wowza JAR will be provided"

# Install Wowza JAR if it exists
RUN if [ -f /wowza-libs/wms-server.jar ]; then \
    mvn install:install-file \
      -Dfile=/wowza-libs/wms-server.jar \
      -DgroupId=com.wowza \
      -DartifactId=wms-server \
      -Dversion=4.8.0 \
      -Dpackaging=jar; \
    fi

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Extract the Wowza module JAR
RUN cp target/*-wowza-module.jar /output/wowza-custom-module.jar || \
    cp target/*.jar /output/wowza-custom-module.jar

# Runtime stage
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
COPY --from=builder /output/wowza-custom-module.jar /output/

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]