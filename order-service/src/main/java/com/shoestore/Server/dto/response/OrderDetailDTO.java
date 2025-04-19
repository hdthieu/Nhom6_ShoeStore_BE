package com.shoestore.Server.dto.response;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Long orderId;
    private int productDetail; // ID sản phẩm chi tiết
    private int quantity;
    private double price;
}

