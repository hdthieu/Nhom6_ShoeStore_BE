package com.shoestore.Server.repositories;

import com.shoestore.Server.dto.ProductDTO;
import com.shoestore.Server.entities.Color;
import com.shoestore.Server.entities.Product;
import com.shoestore.Server.entities.ProductDetail;
import com.shoestore.Server.entities.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> , JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p JOIN p.productDetails pd WHERE pd.productDetailID = :productDetailID")
    Product findProductByProductDetailId(@Param("productDetailID") int productDetailID);
    List<Product> findByProductID(int productID);


    // này của hieu
//    @Query("SELECT p FROM Product p WHERE p.productID NOT IN :productIDs")
//    List<Product> findByProductIDNotIn(@Param("productIDs") List<Integer> productIDs);
//
//    // Best Sellers: Top 10 products sorted by total sales quantity
//        @Query("SELECT new com.shoestore.Server.dto.ProductDTO(p.productID, p.productName, p.price, p.status, " +
//                "p.brand.name, p.category.name, p.description, p.createDate, SUM(od.quantity)) " +
//                "FROM Product p JOIN OrderDetail od ON p.productID = od.product.productID " +
//                "GROUP BY p.productID, p.productName, p.price, p.status, p.brand.name, p.category.name, " +
//                "p.description, p.createDate " +
//                "ORDER BY SUM(od.quantity) DESC")
//        List<ProductDTO> findTop10BestSellers(Pageable pageable);
//
//    // New Arrivals: Top 10 newest products sorted by creation date
//    @Query("SELECT new com.shoestore.Server.dto.ProductDTO(p.productID, p.productName, p.price, p.status, " +
//            "p.brand.name, p.category.name, p.description, p.createDate, 0) " +
//            "FROM Product p ORDER BY p.createDate DESC")
//    List<ProductDTO> findTop10NewArrivals(Pageable pageable);
//
//    // Trending: Top 10 products added to carts the most
//    @Query("SELECT new com.shoestore.Server.dto.ProductDTO(p.productID, p.productName, p.price, p.status, " +
//            "p.brand.name, p.category.name, p.description, p.createDate, COUNT(ci.id)) " +
//            "FROM Product p JOIN CartItem ci ON p.productID = ci.product.productID " +
//            "GROUP BY p.productID, p.productName, p.price, p.status, p.brand.name, p.category.name, " +
//            "p.description, p.createDate " +
//            "ORDER BY COUNT(ci.id) DESC")
//    List<ProductDTO> findTop10Trending(Pageable pageable);

}
