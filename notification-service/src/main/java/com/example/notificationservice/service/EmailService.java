package com.example.notificationservice.service;
import jakarta.mail.MessagingException;
import java.util.Map;

public interface EmailService {
    void sendInvoice(String toEmail, Map<String, Object> orderDetails) throws MessagingException;
}
