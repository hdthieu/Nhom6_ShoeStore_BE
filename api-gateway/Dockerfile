# Sử dụng JDK 17 với hình ảnh nhẹ
FROM openjdk:17-jdk-slim

# Định nghĩa thư mục làm việc
WORKDIR /app

# Đặt biến môi trường
ENV APP_NAME=api-gateway
ENV APP_PORT=8765

# Copy file JAR vào container
COPY target/APIGateway-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng ứng dụng
EXPOSE 8765

# Chạy ứng dụng với ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar"]
