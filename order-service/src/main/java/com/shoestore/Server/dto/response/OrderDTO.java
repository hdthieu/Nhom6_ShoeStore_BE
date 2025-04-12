package com.shoestore.Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer orderID;
    private LocalDate dateCreated;
    private String name;
    private double totalPrice;
    private String status;

}
