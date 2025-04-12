package com.shoestore.Server.service;

import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.dto.ProductDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUCT_SERVICE_BASE_URL = "http://localhost:8081";

    // Lấy chi tiết sản phẩm (productDetail)
    public ProductDetailDTO getProductDetail(Integer productDetailID) {
        String url = PRODUCT_SERVICE_BASE_URL + "/product-detail/" + productDetailID;
        return restTemplate.getForObject(url, ProductDetailDTO.class);
    }

    // Lấy thông tin sản phẩm để lấy giá
    public ProductDTO getProduct(Integer productID) {
        String url = PRODUCT_SERVICE_BASE_URL + "/product/" + productID;
        return restTemplate.getForObject(url, ProductDTO.class);
    }
}


