package com.shoestore.Server.service.impl;

import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.entities.Product;
import com.shoestore.Server.entities.ProductCache;
import com.shoestore.Server.repositories.ProductCacheRepository;
import com.shoestore.Server.repositories.ProductRepository;
import com.shoestore.Server.service.ProductService;
import com.shoestore.Server.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCacheRepository productCacheRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // này của hiếu
//    @Autowired
//    private OrderDetailRepository orderDetailRepository;


    public ProductServiceImpl(ProductRepository productRepository, ProductCacheRepository productCacheRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.productCacheRepository = productCacheRepository;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

//    @Override
//    public Product saveProduct(Product product) {
//        return productRepository.save(product);
//    }

    @Override
    public Product saveProduct(Product product) {
        // Lưu sản phẩm vào cơ sở dữ liệu
        Product savedProduct = productRepository.save(product);

        // Tạo đối tượng ProductCache từ sản phẩm đã lưu
        ProductCache productCache = new ProductCache(
                savedProduct.getProductID(),
                savedProduct.getProductName(),
                savedProduct.getImageURL(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStatus(),
                savedProduct.getBrand().getName(),
                savedProduct.getCategory().getName(),
                savedProduct.getSupplier().getSupplierName()
        );

        // Lưu sản phẩm vào Redis cache
        productCacheRepository.save(productCache); // Lưu vào Redis cache
//        clearProductCache();
        return savedProduct;
    }

//    @Override
//    public boolean deleteProduct(int id) {
//        productRepository.deleteById(id);
//        return true;
//    }

    @Override
    public boolean deleteProduct(int id) {
        // Xóa sản phẩm khỏi cơ sở dữ liệu
        productRepository.deleteById(id);

        // Xóa sản phẩm khỏi Redis cache
        productCacheRepository.deleteById(id); // Xóa khỏi Redis cache
        clearProductCache();
        return true;
    }

//    @Override
//    public Product getProductById(int id) {
//        return productRepository.findById(id).orElse(null);
//
//    }

    @Override
    public Product getProductById(int id) {
//        // Kiểm tra trong cache Redis trước (vẫn trả về Product gốc)
//        Product productCache = (Product) redisTemplate.opsForValue().get("product::" + id);
//        if (productCache != null) {
//            return productCache;
//        }

        // Truy vấn DB nếu cache không có
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            // Lưu version rút gọn vào cache để theo dõi bằng Redis Insight
            ProductCache simplified = new ProductCache(
                    product.getProductID(),
                    product.getProductName(),
                    product.getImageURL(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStatus(),
                    product.getBrand() != null ? product.getBrand().getName() : null,
                    product.getCategory() != null ? product.getCategory().getName() : null,
                    product.getSupplier() != null ? product.getSupplier().getSupplierName() : null
            );

            // Lưu bản đơn giản vào Redis (khác key để không ảnh hưởng logic app)
            redisTemplate.opsForValue().set("productCache::" + id, simplified, 10, TimeUnit.MINUTES);
        }

        return product;
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

    @Override
    public Page<Product> findProducts(String keyword, String sortBy, String order, Pageable pageable) {
        Pageable sortedPageable;

        // Kiểm tra nếu `sortBy` và `order` đều không null
        if (sortBy != null && order != null) {
            // Xác định hướng sắp xếp
            Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;

            // Xác định trường cần sắp xếp
            String sortField;
            if ("price".equalsIgnoreCase(sortBy)) {
                sortField = "price";
            } else {
                sortField = "createDate"; // Mặc định là sắp xếp theo createDate
            }

            // Tạo Pageable với thông tin sắp xếp
            sortedPageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(direction, sortField)
            );
        } else {
            // Nếu không có thông tin sắp xếp, dùng Pageable mặc định
            sortedPageable = pageable;
        }

        // Gọi repository với Pageable được điều chỉnh
        return productRepository.findProducts(keyword, sortedPageable);
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

    private void clearProductCache() {
        // Tạo pattern để tìm tất cả các keys liên quan đến "ProductCache"
        Set<String> keys = redisTemplate.keys("productCache:*");

        // Nếu có keys thì xóa chúng
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);  // Xóa tất cả các keys tìm được
        }
    }

}
