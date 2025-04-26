package com.shoestore.Server.controller;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/ImageProduct/{productId}/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable int productId, @PathVariable String imageName) {
        try {
            // 🔥 Đường dẫn tuyệt đối từ project root
            Path imagePath = Paths.get("../uploads/images/" + imageName).toAbsolutePath().normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaTypeFactory.getMediaType(resource)
                                .orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .body(resource);
            } else {
                System.out.println("❌ Không tìm thấy ảnh tại: " + imagePath);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc ảnh: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
