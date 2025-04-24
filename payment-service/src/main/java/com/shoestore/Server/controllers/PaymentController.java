package com.shoestore.Server.controllers;

import com.shoestore.Server.client.OrderClient;
import com.shoestore.Server.entities.Payment;
import com.shoestore.Server.repositories.PaymentRepository;
import com.shoestore.Server.services.PaymentService;
import com.shoestore.Server.services.VNPAYService;
import com.shoestore.Server.utils.HMACUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private VNPAYService vnPayService;

    @PostMapping("/add")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ========================== VNPay Payment ===========================

    // ✅ Gọi VNPayService để tạo link thanh toán
    @GetMapping("/vnpay")
    public ResponseEntity<String> createVNPAY(@RequestParam int amount,
                                              @RequestParam String orderInfo,
                                              @RequestParam String baseUrl,
                                              HttpServletRequest request) {
        try {
            String paymentUrl = vnPayService.createOrder(request, amount, orderInfo, baseUrl);
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi tạo link thanh toán: " + e.getMessage());
        }
    }

    @GetMapping("/vnpay_return")
    public RedirectView vnpayReturn(@RequestParam Map<String, String> params) {
        int status = vnPayService.verifyReturn(params);

        String orderInfo = params.get("vnp_OrderInfo");
        int orderId = Integer.parseInt(orderInfo.replaceAll("[^0-9]", ""));

        if (status == 1) {
            paymentService.updateStatusByOrderId(orderId, "Completed");
//            orderClient.updateOrderStatus(orderId, "Paid");
            return new RedirectView("http://localhost:3000/ordersuccess");
        }
        return new RedirectView("http://localhost:3000/orderfail");
    }


}
