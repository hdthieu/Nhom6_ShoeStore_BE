package com.shoestore.Server.service;

import com.shoestore.Server.dto.CartDTO;
import com.shoestore.Server.entities.Cart;

public interface CartService {
    Cart getCartByUserId(int id);

    Cart createCartForUser(int userId);

    CartDTO saveCart(CartDTO cartDTO);
}