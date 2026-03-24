# Базовий образ Maven + JDK 17
FROM maven:3.9.4-eclipse-temurin-17-alpine AS build

# Робоча директорія
WORKDIR /app

# Копіюємо проект
COPY . .

# Збираємо JAR без тестів
RUN mvn clean package -DskipTests

# ======== Фаза запуску ========
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Копіюємо зібраний JAR з попереднього образу
COPY --from=build /app/target/demo2.jar .

# Команда запуску
CMD ["java", "-jar", "demo2.jar"]

# Порт
EXPOSE 8080