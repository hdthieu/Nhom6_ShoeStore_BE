package com.shoestore.Server.repositories;


import com.shoestore.Server.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer> {
    List<ProductDetail> findByProduct_ProductID(int productID);
}


