package com.shoestore.Server.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderDTO {
    private Integer orderID;
    private LocalDate dateCreated;
    private String name;
    private double totalPrice;
    private String status;
    private Integer userId;
    private Integer voucherID;
}
