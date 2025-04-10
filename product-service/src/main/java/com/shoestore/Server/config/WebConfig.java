package com.shoestore.Server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Áp dụng cho tất cả các endpoint
//                        .allowedOrigins("http://localhost:3000") // Cho phép client từ localhost:9090
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Các phương thức HTTP cho phép
//            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Tính đường dẫn tuyệt đối tới thư mục lưu ảnh: ../uploads/images
                String imagePath = Paths.get(System.getProperty("user.dir"))
                        .getParent()                   // Lên 1 cấp từ product-service
                        .resolve("uploads")
                        .resolve("images")
                        .toUri()
                        .toString();                   // Đường dẫn dạng file:///...

                registry.addResourceHandler("/images/**")
                        .addResourceLocations(imagePath);
            }
        };
    }
}

