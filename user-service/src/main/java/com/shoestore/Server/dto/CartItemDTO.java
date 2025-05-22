package com.shoestore.Server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDTO {
    private int cartId;
    private int productDetailId;
    private int quantity;
    private double subTotal;

    private String productName;
    private String productImage;
    private double productPrice;
    private String color;
    private String size;

//    @Transient
//    private int stockQuantity;
}


