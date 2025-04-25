package com.shoestore.Server.repositories;
import com.shoestore.Server.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shoestore.Server.entities.Payment;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    // ✅ Đúng cú pháp để truy cập vào field payment.paymentID
    List<Receipt> findByPayment_PaymentID(Integer paymentId);
}