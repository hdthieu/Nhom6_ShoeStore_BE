package com.shoestore.Server.service;

import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();
    Product findByProductDetailsId(int id);
    public Product saveProduct(Product product);
    public boolean deleteProduct(int id);

    public Product getProductById(int id);
//    // nay cua hieu
//    public List<Product> getProductsNotInOrderDetail(int orderID);
//
//    //  Home
//    List<ProductDTO> getTop10BestSellers();
//
//    List<ProductDTO> getTop10NewArrivals();
//
//    List<ProductDTO> getTop10Trending();

    List<Product> getFilteredProducts(List<Integer> categoryIds, List<Integer> brandIds, List<String> colors, List<String> sizes, Double minPrice, Double maxPrice,String sortBy);
}
