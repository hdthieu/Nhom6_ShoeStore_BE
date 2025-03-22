# Sử dụng JDK 17 với hình ảnh nhẹ
FROM openjdk:17-jdk-slim

# Định nghĩa thư mục làm việc
WORKDIR /app

# Đặt biến môi trường
ENV APP_NAME=eureka-server
ENV APP_PORT=8761

# Copy file JAR vào container
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
#COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar


# Mở cổng ứng dụng
EXPOSE 8761

# Chạy ứng dụng với ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar"]
