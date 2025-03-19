package com.shoestore.Server.repositories;

import com.shoestore.Server.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

//    // Tìm OrderDetail theo productID và orderID
//    Optional<OrderDetail> findByProductProductIDAndOrderOrderID(int productID, int orderID);
//
//    @Modifying
//    @Query("DELETE FROM OrderDetail od WHERE od.product.productID = :productID AND od.order.orderID = :orderID")
//    void deleteByProductProductIDAndOrderOrderID(@Param("productID") int productID, @Param("orderID") int orderID);





//    @Query("SELECT od FROM OrderDetail od WHERE od.productDetail.productDetailID = :productDetailID AND od.order.orderID = :orderID")
//    List<OrderDetail> findByProductIDAndOrderID(@Param("productDetailID") int productID, @Param("orderID") int orderID);



    @Query("SELECT od.productDetail AS product, SUM(od.quantity) AS totalQuantity " +
            "FROM OrderDetail od JOIN od.order o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY od.productDetail " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

//    @Query("SELECT od.productDetail.productDetailID FROM OrderDetail od WHERE od.order.orderID = :orderID")
//    List<Integer> findProductIDsByOrderID(@Param("orderID") int orderID);


}

