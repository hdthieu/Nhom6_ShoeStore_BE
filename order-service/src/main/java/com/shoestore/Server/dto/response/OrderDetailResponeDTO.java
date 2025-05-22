package com.shoestore.Server.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDetailResponeDTO {
    private int orderID;
    private int productDetail;
    private int quantity;
    private double price;
}
