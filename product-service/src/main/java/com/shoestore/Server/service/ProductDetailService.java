package com.shoestore.Server.service;


import com.shoestore.Server.entities.ProductDetail;
import com.shoestore.Server.repositories.ProductRepository;

import java.util.List;

public interface ProductDetailService {


    public ProductDetail addProductDetail(ProductDetail productDetail);

    List<ProductDetail> getByProductId(int productId);


    ProductDetail save(ProductDetail productDetail);
    ProductDetail getProductDetailById(int id);
}
