package com.shoestore.Server.controller;

import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.dto.ProductDetailDTO;
import com.shoestore.Server.entities.Cart;
import com.shoestore.Server.entities.CartItem;
import com.shoestore.Server.entities.CartItemKey;
import com.shoestore.Server.service.CartItemService;
import com.shoestore.Server.service.CartService;
import com.shoestore.Server.service.ProductClient;
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
            // Lấy productDetail từ product-service
            ProductDetailDTO productDetail = productClient.getProductDetail(cartItem.getId().getProductDetailId());
            if (productDetail == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy ProductDetail với ID: " + cartItem.getId().getProductDetailId());
            }

            // Lấy productID từ productDetail
            int productId = productDetail.getProductDetailID(); // chú ý: getProductID từ ProductDetailDTO

            // Lấy product từ product-service để lấy giá
            ProductDTO product = productClient.getProduct(productId);
            if (product == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy Product với ID: " + productId);
            }

            // Tính subTotal
            double price = product.getPrice();
            int quantity = cartItem.getQuantity();
            cartItem.setSubTotal(price * quantity);

            // Thêm vào CartItem
            CartItem savedCartItem = cartItemService.addCartItem(cartItem);
            return ResponseEntity.ok(savedCartItem);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
