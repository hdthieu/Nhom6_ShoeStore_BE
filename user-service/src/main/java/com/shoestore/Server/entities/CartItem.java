package com.shoestore.Server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@NoArgsConstructor
@ToString
public class CartItem {
    @EmbeddedId
    private CartItemKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartId")
    @JoinColumn(name = "cartID")
    @JsonProperty("cart")
    @JsonIgnore
    private Cart cart;

    private int quantity;
    private double subTotal;

    public CartItem(CartItemKey id, Cart cart, int quantity, double subTotal) {
        this.id = id;
        this.cart = cart;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }
}
