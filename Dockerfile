# Estágio de construção
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Estágio de produção
FROM openjdk:11.0.13-jre-slim
COPY --from=build /app/target/invoice_generator.jar /app/invoice_generator.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/invoice_generator.jar"]
