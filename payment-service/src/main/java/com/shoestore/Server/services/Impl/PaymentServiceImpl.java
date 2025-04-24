package com.shoestore.Server.services.Impl;

import com.shoestore.Server.entities.Payment;
import com.shoestore.Server.repositories.PaymentRepository;
import com.shoestore.Server.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }
    @Override
    public Payment updateStatus(int paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment với ID: " + paymentId));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
    @Override
    public Optional<Payment> getByOrderId(int orderId) {
        return paymentRepository.findByOrderID(orderId);
    }

    @Override
    public void updateStatusByOrderId(int orderId, String newStatus) {
        Optional<Payment> paymentOpt = paymentRepository.findByOrderID(orderId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setStatus(newStatus);
            paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Không tìm thấy payment theo orderId = " + orderId);
        }
    }

}
