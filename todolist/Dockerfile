# --- Estágio de Build ---
# Usa uma imagem do Maven com Java 17 para compilar o projeto.
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia os arquivos de configuração do Maven.
COPY pom.xml .

# Baixa as dependências (isso cria um cache, acelerando builds futuros).
RUN mvn dependency:go-offline

# Copia o resto do código fonte.
COPY src ./src

# Executa o build do Maven, gerando o arquivo .jar. -DskipTests pula os testes.
RUN mvn package -DskipTests

# --- Estágio de Execução ---
# Usa uma imagem base do Java 17, que é muito menor e mais segura.
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho.
WORKDIR /app

# Copia apenas o arquivo .jar gerado no estágio de build.
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot usa (padrão 8080).
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner rodar.
ENTRYPOINT ["java", "-jar", "app.jar"]