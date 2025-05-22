package com.shoestore.Server.controller;

import com.shoestore.Server.clients.ProductClient;
import com.shoestore.Server.dto.CartDTO;
import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.dto.ProductDetailDTO;
import com.shoestore.Server.entities.Cart;
import com.shoestore.Server.entities.CartItem;
import com.shoestore.Server.entities.CartItemKey;
import com.shoestore.Server.service.CartItemService;
import com.shoestore.Server.service.CartService;

import com.shoestore.Server.clients.ProductClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ProductClient productClient;

    public CartController(CartItemService cartItemService, CartService cartService, ProductClient productClient) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.productClient = productClient;
    }

    @PutMapping("/update/{cartId}/{productDetailId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable("cartId") int cartId,
                                                   @PathVariable("productDetailId") int productDetailId,
                                                   @RequestBody CartItem cartItem) {
        CartItemKey cartItemKey = new CartItemKey(cartId, productDetailId);
        CartItem updated = cartItemService.updateQuantity(cartItemKey, cartItem);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete/{cartId}/{productDetailId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("cartId") int cartId,
                                                 @PathVariable("productDetailId") int productDetailId) {
        CartItemKey key = new CartItemKey(cartId, productDetailId);
        cartItemService.deleteCartItem(key);
        return ResponseEntity.ok("CartItem deleted");
    }

    @GetMapping("/{cartId}/{productDetailId}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable("cartId") int cartId,
                                                    @PathVariable("productDetailId") int productDetailId) {
        CartItemKey key = new CartItemKey(cartId, productDetailId);
        CartItem item = cartItemService.getCartItemById(key);
        return item != null
                ? ResponseEntity.ok(item)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/userid/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable("userId") int userId) {
        Cart cart = cartService.getCartByUserId(userId);

        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                int productDetailId = item.getId().getProductDetailId();

                try {
                    ProductDetailDTO dto = productClient.getProductDetailWithProduct(productDetailId);
                    item.setStockQuantity(dto.getStockQuantity());
                } catch (Exception e) {
                    System.err.println("❌ Lỗi khi gọi productClient: " + e.getMessage());
                    item.setStockQuantity(0); // fallback nếu gọi lỗi
                }
            }
        }

        return ResponseEntity.ok(cart);
    }

    @GetMapping("/cart-item/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable("cartId") int cartId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestBody CartItem cartItem) {
        try {
            int productDetailId = cartItem.getId().getProductDetailId();

            // ✅ Gọi API đảm bảo ProductDetail có Product
            ProductDetailDTO productDetail = productClient.getProductDetailWithProduct(productDetailId);
            if (productDetail == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy ProductDetail với ID: " + productDetailId);
            }

            ProductDTO product = productDetail.getProduct();
            if (product == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy Product trong ProductDetail với ID: " + productDetailId);
            }

            // ✅ Tính subtotal
            double price = product.getPrice();
            cartItem.setSubTotal(price * cartItem.getQuantity());

            // ✅ Gọi service để lưu cartItem
            CartItem savedCartItem = cartItemService.addCartItem(cartItem);
            return ResponseEntity.ok(savedCartItem);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi hệ thống: " + e.getMessage());
        }
    }
    @PutMapping("/item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody CartItem cartItem) {
        if (cartItem == null || cartItem.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        CartItem updatedItem = cartItemService.updateQuantity(cartItem.getId(), cartItem);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Cart> createCartForUser(@RequestBody int userId) {
        // Kiểm tra nếu người dùng đã có giỏ hàng
        Cart existingCart = cartService.getCartByUserId(userId);
        if (existingCart != null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Tạo giỏ hàng mới
        Cart newCart = cartService.createCartForUser(userId);
        return ResponseEntity.ok(newCart);
    }
    @PostMapping("/save")
    public ResponseEntity<CartDTO> saveCart(@RequestBody CartDTO cartDTO) {
        try {
            return ResponseEntity.ok(cartService.saveCart(cartDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }






}
