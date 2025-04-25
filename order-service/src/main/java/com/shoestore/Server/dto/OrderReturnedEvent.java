package com.shoestore.Server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.io.Serializable;
public class OrderReturnedEvent {
    private int orderId;
    private List<ProductQuantity> items;

    public static class ProductQuantity {
        private int productId;
        private int quantity;
        private String color;   // Thêm color
        private String size;    // Thêm size

        // Getter và Setter cho các trường
        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<ProductQuantity> getItems() {
        return items;
    }

    public void setItems(List<ProductQuantity> items) {
        this.items = items;
    }
}
