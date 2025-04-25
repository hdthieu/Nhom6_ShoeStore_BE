package com.shoestore.Server.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDetailRequestDTO {
    private double price;
    private int quantity;
    private int productDetailId;
    private int orderId;
    private Integer voucherID;
}

