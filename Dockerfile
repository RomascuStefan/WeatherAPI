FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml ./
COPY src/ src/
RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
