package com.shoestore.Server.controller;


import com.shoestore.Server.entities.Product;
import com.shoestore.Server.entities.ProductDetail;
import com.shoestore.Server.service.ProductDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products-details")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }


    @PostMapping
    public ResponseEntity<ProductDetail> addProductDetail(@RequestBody ProductDetail productDetail) {
        try {
            // Lưu sản phẩm vào cơ sở dữ liệu
            ProductDetail savedProductDetail = productDetailService.addProductDetail(productDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDetail);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getProductDetailsByProductId(@PathVariable int id) {
        List<ProductDetail> productDetails=productDetailService.getByProductId(id);
        Map<String,Object> response= new HashMap<>();
        response.put("productDetails",productDetails);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/productDetailId/{id}")
    public ResponseEntity<ProductDetail> getProductDetailsById(@PathVariable int id) {
        ProductDetail productDetail=productDetailService.getProductDetailById(id);
        if (productDetail != null) {
            return ResponseEntity.ok(productDetail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
