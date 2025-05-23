package com.shoestore.Server.services;

import com.shoestore.Server.entities.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment createPayment(Payment payment);
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Integer id);
    Payment updateStatus(int paymentId, String status);
    Optional<Payment> getByOrderId(int orderId);
    //update status theo orderid
    void updateStatusByOrderId(int orderId, String status);
}