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

//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("productDetailId")
//    @JoinColumn(name = "productDetailID")
//    @JsonProperty("productDetail")
//    @JsonIgnore
//    private ProductDetail productDetail;
    private int productDetailID;
    private int quantity;
    private double subTotal;

    public CartItem(CartItemKey id, Cart cart, int productDetailID, int quantity, double subTotal) {
        this.id = id;
        this.cart = cart;
        this.productDetailID = productDetailID;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }
}