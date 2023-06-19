# Imagem base do OpenJDK 17
FROM adoptopenjdk:17-jdk-hotspot

# Diretório de trabalho no container
WORKDIR /app

# Copiar os arquivos necessários para o container
COPY target/invoice_generator-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties

# Porta exposta pela aplicação
EXPOSE 8080

# Comando para executar a aplicação quando o container for iniciado
CMD ["java", "-jar", "app.jar"]
