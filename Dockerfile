FROM maven:3.9.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build target/notebook-0.0.1-SNAPSHOT.jar notebook.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]