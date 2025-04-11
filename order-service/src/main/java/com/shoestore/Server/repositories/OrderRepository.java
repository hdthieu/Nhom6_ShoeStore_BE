package com.shoestore.Server.repositories;

import com.shoestore.Server.entities.Order;
import com.shoestore.Server.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserID(int userId);
    public Order findByOrderID(int orderID);
    @Query("SELECT COUNT(DISTINCT o.orderID) as totalOrders, " +
            "SUM(od.quantity) AS totalQuantity, " +
            "SUM(od.quantity * od.price) AS totalRevenue, " +
            "o.voucherID " +
            "FROM OrderDetail od " +
            "JOIN od.order o " +
            "WHERE YEAR(o.orderDate) = :year AND o.voucherID > 0 " +
            "GROUP BY o.voucherID")
    List<Object[]> findTotalRevenueAndQuantityByYear(@Param("year") int year);



//    @Query("SELECT o.user, COUNT(o), SUM(od.quantity * od.price - " +
//            "CASE " +
//            "WHEN o.voucher IS NOT NULL AND o.voucher.discountType = 'Percentage' THEN (od.quantity * od.price * o.voucher.discountValue / 100) " +
//            "WHEN o.voucher IS NOT NULL AND o.voucher.discountType = 'Flat' THEN o.voucher.discountValue " +
//            "ELSE 0 END) " +
//            "FROM Order o " +
//            "JOIN o.orderDetails od " +
//            "WHERE o.user.role.name = 'Customer' " +
//            "GROUP BY o.user " +
//            "HAVING COUNT(o) >= :minOrders " +
//            "ORDER BY SUM(od.quantity * od.price - " +
//            "CASE " +
//            "WHEN o.voucher IS NOT NULL AND o.voucher.discountType = 'Percentage' THEN (od.quantity * od.price * o.voucher.discountValue / 100) " +
//            "WHEN o.voucher IS NOT NULL AND o.voucher.discountType = 'Flat' THEN o.voucher.discountValue " +
//            "ELSE 0 END) DESC")
//    List<Object[]> findLoyalCustomers(@Param("minOrders") int minOrders);


    long countByStatus(String status);
    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

}

