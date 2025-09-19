# Multi-stage Dockerfile for building the Spring Boot application

# Build stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY mvnw ./
COPY .mvn/ .mvn/
COPY src/ ./src/
COPY sonar-project.properties ./
RUN mvn -B -e -ntp -DskipTests -Denforcer.skip=true package

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
COPY src/main/docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]
