# ======= Фаза збірки =======
# Базовий образ Maven + JDK 17 для збірки
FROM maven:3.9.4-eclipse-temurin-17-alpine AS build

# Робоча директорія
WORKDIR /app

# Копіюємо весь проект у контейнер
COPY . .

# Збираємо проект без тестів
RUN mvn clean package -DskipTests

# ======= Фаза запуску =======
# Легкий JDK 17 образ для запуску
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Копіюємо зібраний JAR з попередньої фази
COPY --from=build /app/target/*.jar demo2.jar

# Команда запуску сервера
CMD ["java", "-jar", "demo2.jar"]

# Порт, який слухає твій сервер
EXPOSE 8080