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
RUN apk add --no-cache curl
WORKDIR /app
# copy the produced jar (adjust artifact name if different)
COPY --from=builder /build/target/config-server-0.0.1-SNAPSHOT.jar /app/config-server.jar
ENTRYPOINT ["java", "-jar", "/app/config-server.jar"]

