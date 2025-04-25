package com.shoestore.Server.repositories;

import com.shoestore.Server.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findByOrderID(int orderId);
}
