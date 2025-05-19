package com.shoestore.Server.service.impl;


import com.shoestore.Server.clients.ProductClient;
import com.shoestore.Server.dto.CartItemDTO;
import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.dto.ProductDetailDTO;
import com.shoestore.Server.entities.Cart;
import com.shoestore.Server.entities.CartItem;
import com.shoestore.Server.entities.CartItemKey;
import com.shoestore.Server.repositories.CartItemRepository;
import com.shoestore.Server.repositories.CartRepository;
import com.shoestore.Server.service.CartItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {



    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public List<CartItemDTO> getCartItemsByCartId(Integer cartId) {
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cartId);
        List<CartItemDTO> response = new ArrayList<>();

        for (CartItem item : cartItems) {
            int productDetailId = item.getId().getProductDetailId();

            // ✅ Gọi API mới để lấy cả ProductDetail và Product
            ProductDetailDTO productDetail = productClient.getProductDetailWithProduct(productDetailId);
            if (productDetail == null || productDetail.getProduct() == null) continue;

            CartItemDTO dto = new CartItemDTO();

            // ✅ Set id
            CartItemDTO.IdDTO idDTO = new CartItemDTO.IdDTO();
            idDTO.setCartId(item.getId().getCartId());
            idDTO.setProductDetailId(productDetail.getProductDetailID());
            dto.setId(idDTO);

            // ✅ Set product
            dto.setProduct(productDetail.getProduct());

            // ✅ Set productDetails
            dto.setProductDetails(List.of(productDetail));

            // ✅ Set quantity & subtotal
            dto.setQuantity(item.getQuantity());
            dto.setSubTotal(item.getSubTotal());

            response.add(dto);
        }

        return response;
    }




    @Override
    public CartItem addCartItem(CartItem cartItem) {
        CartItemKey key = cartItem.getId();

        Cart cart = cartRepository.findById(key.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        ProductDetailDTO productDetail = productClient.getProductDetailWithProduct(key.getProductDetailId());
        if (productDetail == null || productDetail.getProduct() == null) {
            throw new IllegalArgumentException("ProductDetail hoặc Product bên trong null");
        }

        ProductDTO product = productDetail.getProduct();

        // ✅ Gán lại các thông tin
        cartItem.setId(new CartItemKey(cart.getCartID(), productDetail.getProductDetailID()));
        cartItem.setCart(cart);
        cartItem.setSubTotal(product.getPrice() * cartItem.getQuantity());

        // ❌ Không cần set productDetailID vì không tồn tại nữa trong entity

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem getCartItemById(CartItemKey cartItemKey) {
        return cartItemRepository.findById(cartItemKey).orElse(null);
    }

    @Override
    public CartItem updateQuantity(CartItemKey id, CartItem cartItem) {
        Optional<CartItem> opt = cartItemRepository.findById(id);
        if (opt.isPresent()) {
            CartItem existingItem = opt.get();

            // Cập nhật số lượng
            existingItem.setQuantity(cartItem.getQuantity());

            // Cập nhật subtotal nếu cần
            ProductDetailDTO productDetail = productClient.getProductDetailWithProduct(id.getProductDetailId());
            if (productDetail != null && productDetail.getProduct() != null) {
                double price = productDetail.getProduct().getPrice();
                existingItem.setSubTotal(price * cartItem.getQuantity());
            }

            return cartItemRepository.save(existingItem);
        }
        return null;
    }


    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        // Trả về list CartItem entity
        return cartItemRepository.findCartItemsByCartId(cartId);
    }







    @Override
    public void deleteCartItem(CartItemKey id) {
        cartItemRepository.deleteById(id);
    }


//    @Override
//    public CartItem getCartItemById(CartItemKey cartItemKey) {
//        return cartItemRepository.findById(cartItemKey).orElse(null);
//    }
//
//    @Override
//    public CartItem updateQuantity(CartItemKey id, CartItem cartItem) {
//        Optional<CartItem> existCartItem= cartItemRepository.findById(id);
//        if(existCartItem.isPresent()){
//            CartItem entityCartItem=existCartItem.get();
//
//            entityCartItem.setQuantity(cartItem.getQuantity());
//            entityCartItem.setCart(cartItem.getCart());
//            entityCartItem.setProductDetail(cartItem.getProductDetail());
//            return cartItemRepository.save(entityCartItem);
//        }
//        return null;
//    }
}

