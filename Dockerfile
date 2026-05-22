# Build stage
FROM maven:3.9.9 AS builder
WORKDIR /workspace
COPY backend/pom.xml ./
COPY backend/src ./src
RUN mvn -B package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /workspace/target/job-jugad-0.0.1-SNAPSHOT.jar ./job-jugad.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "job-jugad.jar"]
