package com.shoestore.Server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;

    private LocalDate paymentDate;
    private String status;
    private Integer orderID;
    @OneToMany(mappedBy = "payment")
    private List<Receipt> receipts;

    // Getter, Setter
}
