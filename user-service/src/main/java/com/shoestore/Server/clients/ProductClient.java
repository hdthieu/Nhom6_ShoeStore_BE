package com.shoestore.Server.clients;

import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.dto.ProductDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8765")
public interface ProductClient {

//
@GetMapping("/products-details/detailWithProduct/{id}")
ProductDetailDTO getProductDetailWithProduct(@PathVariable("id") int id);

    // ✅ Đây là đường dẫn đúng đã được BE expose (không còn lỗi 404)
    @GetMapping("/products/detailFor/{id}")
    ProductDTO getProduct(@PathVariable("id") int id);
}
