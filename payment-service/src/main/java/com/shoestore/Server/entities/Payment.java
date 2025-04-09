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
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private int paymentID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID") // Khóa ngoại ánh xạ đến Order
    private Order order;

    @Column(name = "orderID", insertable = false, updatable = false) // Đánh dấu chỉ đọc để tránh lỗi trùng lặp
    private int orderID;

    private LocalDate paymentDate;
    private String status;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private Receipt receipt;
}
