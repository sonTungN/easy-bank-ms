# dockerfile
# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

# dependency
WORKDIR /build
COPY pom.xml mvnw* ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# build
COPY src src
RUN ./mvnw -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre-alpine AS runner
LABEL maintainer="easybanks.com"
WORKDIR /app

# copy the produced jar (adjust artifact name if different)
COPY --from=builder /build/target/account-0.0.1-SNAPSHOT.jar /app/account.jar
ENTRYPOINT ["java", "-jar", "/app/account.jar"]