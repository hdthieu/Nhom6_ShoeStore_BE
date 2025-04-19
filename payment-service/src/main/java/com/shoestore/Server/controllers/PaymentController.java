package com.shoestore.Server.controllers;

import com.shoestore.Server.client.OrderClient;
import com.shoestore.Server.config.VnpayConfig;
import com.shoestore.Server.dto.OrderDTO;
import com.shoestore.Server.entities.Payment;
import com.shoestore.Server.services.PaymentService;
import com.shoestore.Server.utils.HMACUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/add")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(payment));
    }

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        System.out.println("Số lượng payment tìm thấy: " + payments.size());
        return payments;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/vnpay")
    public ResponseEntity<?> createVnpayPayment(@RequestParam int orderId, @RequestParam int amount) {
        try {
            Map<String, Object> orderData = (Map<String, Object>) orderClient.getOrderById(orderId);
            if (orderData == null || orderData.isEmpty()) {
                throw new RuntimeException("Không tìm thấy đơn hàng từ Order Service");
            }

            String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
            String vnp_OrderInfo = "Thanh toán đơn hàng: " + orderId;
            String vnp_Amount = String.valueOf(amount * 100); // VNPay yêu cầu nhân 100
            String vnp_ReturnUrl = VnpayConfig.vnp_ReturnUrl;
            String vnp_IpAddr = "127.0.0.1";

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
            vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
            vnp_Params.put("vnp_TmnCode", VnpayConfig.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", vnp_Amount);
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
            vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            for (String fieldName : fieldNames) {
                String value = vnp_Params.get(fieldName);
                if (value != null && !value.isEmpty()) {
                    hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                    query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                }
            }

            String queryUrl = query.toString().substring(0, query.length() - 1);
            String hashValue = HMACUtil.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString().substring(0, hashData.length() - 1));
            String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl + "&vnp_SecureHash=" + hashValue;

            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo thanh toán VNPay: " + e.getMessage());
        }
    }


}

