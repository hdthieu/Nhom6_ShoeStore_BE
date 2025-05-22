package com.shoestore.Server.dto;

import com.shoestore.Server.entities.Color;
import com.shoestore.Server.entities.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderReturnedEvent {
    private int orderId;
    private List<ProductQuantity> items;

    @Getter
    @Setter
    public static class ProductQuantity {
        private int productId;
        private int quantity;
        private Color color;
        private Size size;
    }
}
