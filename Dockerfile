# Imagem base do OpenJDK 11
FROM adoptopenjdk:11-jdk-hotspot

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo JAR da aplicação para o container
COPY target/Invoice_generator.jar app.jar

# Porta exposta pela aplicação
EXPOSE 8080

# Comando para executar a aplicação quando o container for iniciado
CMD ["java", "-jar", "app.jar"]
