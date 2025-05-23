package com.shoestore.Server.service.impl;

import com.shoestore.Server.dto.CartDTO;
import com.shoestore.Server.dto.CartItemDTO;
import com.shoestore.Server.entities.Cart;
import com.shoestore.Server.entities.CartItem;
import com.shoestore.Server.entities.User;
import com.shoestore.Server.repositories.CartItemRepository;
import com.shoestore.Server.repositories.CartRepository;
import com.shoestore.Server.repositories.UserRepository;
import com.shoestore.Server.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart getCartByUserId(int id) {
        return cartRepository.findCartByUserId(id);
    }

//    @Override
//    public Cart createCartForUser(int userId) {
//        Cart existingCart = cartRepository.findCartByUserId(userId);
//        if (existingCart != null) {
//            return existingCart; // đã có giỏ hàng rồi
//        }
//        Cart newCart = new Cart();
//
//        // Tạo User chỉ với id (nếu không cần load dữ liệu user đầy đủ)
//        User user = new User();
//        user.setUserID(userId);
//
//        newCart.setUser(user);
//
//        // Khởi tạo thêm thuộc tính khác nếu cần
//
//        return cartRepository.save(newCart);
//    }

    @Override
    public Cart createCartForUser(int userId) {
        Cart existingCart = cartRepository.findCartByUserId(userId);
        if (existingCart != null) {
            return existingCart;
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        Cart newCart = new Cart();
        newCart.setUser(optionalUser.get());

        // khởi tạo các thuộc tính khác nếu cần

        return cartRepository.save(newCart);
    }

    @Override
    public CartDTO saveCart(CartDTO cartDTO) {
        return null;
    }

    public Cart saveCart(Cart cart) {
        // Lưu giỏ hàng
        Cart savedCart = cartRepository.save(cart);

        // Liên kết và lưu các sản phẩm
        if (cart.getCartItems() != null) {
            cart.getCartItems().forEach(item -> item.setCart(savedCart));
            cartItemRepository.saveAll(cart.getCartItems());
        }

        return savedCart;
    }
}
