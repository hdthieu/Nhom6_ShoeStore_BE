package com.shoestore.Server.service.impl;

import com.shoestore.Server.entities.Product;
import com.shoestore.Server.repositories.ProductRepository;
import com.shoestore.Server.service.ProductService;
import com.shoestore.Server.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    // này của hiếu
//    @Autowired
//    private OrderDetailRepository orderDetailRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findByProductDetailsId(int id) {
        return productRepository.findProductByProductDetailId(id);
    }

    @Override

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(int id) {
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);

    }


    @Override
    public List<Product> getFilteredProducts(List<Integer> categoryIds, List<Integer> brandIds, List<String> colors, List<String> sizes, Double minPrice, Double maxPrice, String sortBy) {
        Specification<Product> spec = Specification.where(null);

        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategories(categoryIds));
        }

        if (brandIds != null && !brandIds.isEmpty()) {
            spec = spec.and(ProductSpecification.hasBrands(brandIds));
        }

        if (colors != null && !colors.isEmpty()) {
            spec = spec.and(ProductSpecification.hasColors(colors));
        }

        if (sizes != null && !sizes.isEmpty()) {
            spec = spec.and(ProductSpecification.hasSizes(sizes));
        }

        if (minPrice != null) {
            spec = spec.and(ProductSpecification.hasMinPrice(minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.hasMaxPrice(maxPrice));
        }

        if (sortBy != null) {
            switch (sortBy) {
                case "Price: High-Low":
                    return productRepository.findAll(spec, Sort.by(Sort.Order.desc("price")));
                case "Price: Low-High":
                    return productRepository.findAll(spec, Sort.by(Sort.Order.asc("price")));
                case "Newest":
                    return productRepository.findAll(spec, Sort.by(Sort.Order.desc("createDate")));
                default:
                    return productRepository.findAll(spec);
            }
        }

        return productRepository.findAll(spec);
    }



    // nay cua hieu
//    public List<Product> getProductsNotInOrderDetail(int orderID) {
//        List<Integer> productIDsInOrderDetail = orderDetailRepository.findProductIDsByOrderID(orderID);
//        if (productIDsInOrderDetail.isEmpty()) {
//            return productRepository.findAll();
//        } else {
//            return productRepository.findByProductIDNotIn(productIDsInOrderDetail);
//        }
//    }
//
//    @Override
//    public List<ProductDTO> getTop10BestSellers() {
//        return productRepository.findTop10BestSellers(PageRequest.of(0, 10));
//    }
//
//    @Override
//    public List<ProductDTO> getTop10NewArrivals() {
//        return productRepository.findTop10NewArrivals(PageRequest.of(0, 10));
//    }
//
//    @Override
//    public List<ProductDTO> getTop10Trending() {
//        return productRepository.findTop10Trending(PageRequest.of(0, 10));
//    }



}
