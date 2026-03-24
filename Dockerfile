# =====================================
# Dockerfile для Java-проекту demo2
# =====================================

# Базовий образ з Java 17
FROM eclipse-temurin:17-jdk-alpine

# Робоча директорія в контейнері
WORKDIR /app

# Копіюємо весь проект у контейнер
COPY . .

# Якщо використовуєш Maven Wrapper:
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Вказуємо JAR файл для запуску
CMD ["java", "-jar", "target/demo2.jar"]

# Порт, який слухає сервер
EXPOSE 8080