# dockerfile
# Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml mvnw* ./
COPY .mvn .mvn
COPY src src
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="easybanks.com"
WORKDIR /app
# copy the produced jar (adjust artifact name if different)
COPY --from=builder /build/target/CardService-0.0.1-SNAPSHOT.jar /app/CardService.jar
ENTRYPOINT ["java", "-jar", "/app/CardService.jar"]