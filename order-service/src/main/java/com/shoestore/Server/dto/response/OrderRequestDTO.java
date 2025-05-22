package com.shoestore.Server.dto.response;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OrderRequestDTO {
    private double feeShip;
    private double total;
    private LocalDate orderDate;
    private String status;
    private String shippingAddress;
    private UserDTO user;
    private Integer voucherID;
}
