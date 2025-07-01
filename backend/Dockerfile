# Multi-stage build
FROM maven:3.8.6-openjdk-11 AS build

WORKDIR /app

# Copiar pom.xml primeiro para cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fonte e compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Imagem final
FROM openjdk:11-jre-slim

WORKDIR /app

# Copiar o JAR compilado
COPY --from=build /app/target/votacao-backend-1.0.0.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
