package com.example.notificationservice.service.impl;


import com.example.notificationservice.entity.EmailLog;
import com.example.notificationservice.repositories.EmailLogRepository;
import com.example.notificationservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;

    @Override
    public void sendInvoice(String toEmail, Map<String, Object> orderDetails) throws MessagingException {
        String subject = "Thông Tin Hóa Đơn Đặt Hàng";
        String htmlContent = generateInvoiceEmailContent(orderDetails);

        EmailLog.EmailLogBuilder logBuilder = EmailLog.builder()
                .recipient(toEmail)
                .subject(subject)
                .content(htmlContent)
                .sentAt(LocalDateTime.now());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            logBuilder.success(true);
        } catch (MailSendException e) {
            String errorMsg = e.getMessage();
            if (errorMsg != null && errorMsg.contains("Daily user sending limit exceeded")) {
                logBuilder.success(false).errorMessage("Mail send limit exceeded: " + errorMsg);
            } else {
                logBuilder.success(false).errorMessage("Mail send error: " + errorMsg);
            }
        } catch (Exception e) {
            logBuilder.success(false).errorMessage(e.getMessage());
            throw e;
        } finally {
            emailLogRepository.save(logBuilder.build());
        }
    }


    //    @SuppressWarnings("unchecked")
    private String generateInvoiceEmailContent(Map<String, Object> orderDetails) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<html><body>");

        // Header with logo, shop name, and shop details
        emailContent.append("<div style='text-align: center; margin-bottom: 20px;'>");
        emailContent.append("<h2 style='margin: 5px;'>Shop Giày HEHEBOIZ</h2>");
        emailContent.append("<p style='margin: 0;'>Địa chỉ: 123 Shop Street, City</p>");
        emailContent.append("<p style='margin: 0;'>Email: contact@shop.com | Số điện thoại: 0123456789</p>");
        emailContent.append("</div>");

        // Invoice details
        emailContent.append("<h3>Thông tin hóa đơn</h3>");
        emailContent.append("<p><strong>Mã đơn:</strong> " + orderDetails.get("orderID") + "</p>");
        emailContent.append("<p><strong>Trạng thái đơn hàng:</strong> <span style='background: yellow;'>" + orderDetails.get("orderStatus") + "</span></p>");

        // Customer details section
        emailContent.append("<h3>Thông tin khách hàng</h3>");
        emailContent.append("<p><strong>Tên khách hàng:</strong> " + orderDetails.get("customerName") + "</p>");
        emailContent.append("<p><strong>Số điện thoại:</strong> " + orderDetails.get("customerPhone") + "</p>");
        emailContent.append("<p><strong>Địa chỉ giao hàng:</strong> " + orderDetails.get("customerAddress") + "</p>");

        // Product list section
        emailContent.append("<h3>Danh sách sản phẩm:</h3>");
        emailContent.append("<table border='1' cellpadding='10' style='border-collapse: collapse; width: 100%;'>");
        emailContent.append("<tr style='background-color: #f2f2f2;'><th>Sản phẩm</th><th>Số lượng</th><th>Tổng</th></tr>");

        for (Map<String, Object> product : (List<Map<String, Object>>) orderDetails.get("products")) {
            emailContent.append("<tr>");
            emailContent.append("<td>").append(product.get("productName")).append("</td>");
            emailContent.append("<td>").append(product.get("quantity")).append("</td>");
            emailContent.append("<td>").append(product.get("formattedTotalPrice")).append("</td>");
            emailContent.append("</tr>");
        }
        emailContent.append("</table>");

        // Summary
        emailContent.append("<table style='width: 100%; margin-top: 20px;'>");
        emailContent.append("<tr><td style='text-align: right;'><strong>Phí vận chuyển:</strong></td><td style='text-align: right;'>")
                .append(orderDetails.get("feeShip")).append("</td></tr>");
        emailContent.append("<tr><td style='text-align: right;'><strong>Giảm Giá:</strong></td><td style='text-align: right;'>")
                .append(orderDetails.get("discountVoucher")).append("</td></tr>");
        emailContent.append("<tr><td style='text-align: right;'><strong>Tổng tiền đã thanh toán:</strong></td><td style='text-align: right;'>")
                .append(orderDetails.get("totalAmount")).append("</td></tr>");
        emailContent.append("</table>");

        emailContent.append("</body></html>");

        return emailContent.toString();
    }

}
