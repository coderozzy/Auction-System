# Build stage
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /build

# Copy the project files
COPY pom.xml .
COPY src src/

# Build the application
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]