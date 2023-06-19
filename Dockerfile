# Imagem base do OpenJDK 17 para construção
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar os arquivos de configuração e o arquivo pom.xml
COPY pom.xml .
COPY src ./src

# Empacotar o projeto com o Maven
RUN mvn package -DskipTests

# Imagem base do OpenJDK 17 para a aplicação final
FROM openjdk:17-jdk

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo JAR da etapa de construção para o container
COPY --from=build /app/target/invoice_generator-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties

# Porta exposta pela aplicação
EXPOSE 8080

# Comando para executar a aplicação quando o container for iniciado
CMD ["java", "-jar", "app.jar"]
