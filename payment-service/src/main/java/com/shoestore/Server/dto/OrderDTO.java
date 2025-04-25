package com.shoestore.Server.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private int userId;
    private String shippingAddress;
    private int total;
    private int feeShip;
    private String status;
    private LocalDate orderDate;
}
