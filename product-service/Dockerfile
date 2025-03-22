
# Sử dụng JDK 17 với hình ảnh nhẹ
FROM openjdk:17-jdk-slim

# Định nghĩa thư mục làm việc
WORKDIR /app

# Đặt biến môi trường
ENV APP_NAME=PRODUCT-SERVICE
ENV APP_PORT=8081

# Copy file JAR vào container
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng ứng dụng
EXPOSE 8081

# Chạy ứng dụng với ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar"]
