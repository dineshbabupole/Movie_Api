# Use Java 17 JDK base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar into the container
COPY target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080


ENTRYPOINT ["java","-jar","app.jar"]