package com.shoestore.Server.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receiptID;

    private LocalDate receiptDate;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "paymentID")
    private Payment payment;

    // Getter, Setter
}
