package com.shoestore.Server.service.impl;

import com.shoestore.Server.entities.Cart;
import com.shoestore.Server.entities.User;
import com.shoestore.Server.repositories.CartRepository;
import com.shoestore.Server.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCartByUserId(int id) {
        return cartRepository.findCartByUserId(id);
    }

    @Override
    public Cart createCartForUser(int userId) {
        Cart existingCart = cartRepository.findCartByUserId(userId);
        if (existingCart != null) {
            return existingCart; // đã có giỏ hàng rồi
        }
        Cart newCart = new Cart();

        // Tạo User chỉ với id (nếu không cần load dữ liệu user đầy đủ)
        User user = new User();
        user.setUserID(userId);

        newCart.setUser(user);

        // Khởi tạo thêm thuộc tính khác nếu cần

        return cartRepository.save(newCart);
    }

}
