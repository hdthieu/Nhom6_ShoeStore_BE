package com.shoestore.Server.request;

import lombok.Data;

@Data
public class OrderDetailRequestDTO {
    private double price;
    private int quantity;
    private int productDetailId;
    private int orderId;
}

