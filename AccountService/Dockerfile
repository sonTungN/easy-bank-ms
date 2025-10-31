# dockerfile
# Build stage
FROM maven:3.9.4-eclipse-temurin-21 as builder
WORKDIR /build
COPY pom.xml mvnw* ./
COPY .mvn .mvn
COPY src src
RUN mvn -B -DskipTests package

# Runtime stage
FROM openjdk:21-jdk-slim
LABEL maintainer="easybanks.com"
WORKDIR /app
# copy the produced jar (adjust artifact name if different)
COPY --from=builder /build/target/account-0.0.1-SNAPSHOT.jar /app/account.jar
ENTRYPOINT ["java", "-jar", "/app/account.jar"]