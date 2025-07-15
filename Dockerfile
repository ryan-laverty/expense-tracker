# Use a lightweight Java 17 runtime image
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built Spring Boot jar into the container
COPY target/expense-tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]