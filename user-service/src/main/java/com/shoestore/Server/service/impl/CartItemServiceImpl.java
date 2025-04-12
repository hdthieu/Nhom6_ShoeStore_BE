package com.shoestore.Server.service.impl;

import com.shoestore.Server.dto.CartItemDTO;
import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.dto.ProductDetailDTO;
import com.shoestore.Server.entities.CartItem;
import com.shoestore.Server.entities.CartItemKey;
import com.shoestore.Server.repositories.CartItemRepository;
import com.shoestore.Server.service.CartItemService;
import com.shoestore.Server.service.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItemDTO> getCartItemsByCartId(Integer cartId) {
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cartId);
        List<CartItemDTO> response = new ArrayList<>();

        for (CartItem item : cartItems) {
            ProductDetailDTO productDetail = productClient.getProductDetail(item.getProductDetailID());

            CartItemDTO dto = new CartItemDTO();

            // Set id
            CartItemDTO.IdDTO idDTO = new CartItemDTO.IdDTO();
            idDTO.setCartId(item.getCart().getCartID());
            idDTO.setProductId(productDetail.getProductDetailID()); // lấy từ ProductDetail
            dto.setId(idDTO);

            // Set product
            ProductDTO productDTO = productClient.getProductDetail(productDetail.getProductDetailID()).getProduct();
            dto.setProduct(productDTO);

            // Set productDetails (chỉ 1 sản phẩm cụ thể)
            dto.setProductDetails(List.of(productDetail));

            // Set quantity & subtotal
            dto.setQuantity(item.getQuantity());
            dto.setSubTotal(item.getSubTotal());

            response.add(dto);
        }

        return response;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        return List.of();
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        return null;
    }

    @Override
    public CartItem getCartItemById(CartItemKey cartItemKey) {
        return null;
    }

    @Override
    public CartItem updateQuantity(CartItemKey id, CartItem cartItem) {
        return null;
    }

    @Override
    public void deleteCartItem(CartItemKey id) {

    }
}

