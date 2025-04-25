package com.example.notificationservice.controller;

import com.example.notificationservice.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<Map<String, String>> sendInvoiceEmail(@RequestBody Map<String, Object> orderDetails) {
        try {
            String toEmail = (String) orderDetails.get("email");
            emailService.sendInvoice(toEmail, orderDetails);
            return ResponseEntity.ok(Map.of("message", "Email hóa đơn đã được gửi thành công!"));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi gửi email: " + e.getMessage()));
        }
    }

}