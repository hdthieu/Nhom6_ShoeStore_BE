package com.shoestore.Server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private int paymentID;
    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "orderID")
//    private Order order;
    private int OrderID;

    private LocalDate paymentDate;
    private String status;
    @OneToOne(mappedBy = "payment",cascade = CascadeType.ALL)
    private Receipt receipt;
}