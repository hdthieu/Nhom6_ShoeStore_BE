package com.shoestore.Server.service.impl;

import com.shoestore.Server.client.VoucherClient;
import com.shoestore.Server.dto.VoucherDTO;
import com.shoestore.Server.entities.Order;
import com.shoestore.Server.repositories.OrderRepository;
import com.shoestore.Server.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final VoucherClient voucherClient;
    @PersistenceContext
    private EntityManager entityManager;

    public OrderServiceImpl(OrderRepository orderRepository, VoucherClient voucherClient) {
        this.orderRepository = orderRepository;
        this.voucherClient = voucherClient;
    }
    public Map<String, Object> getRevenueAndOrdersForCurrentYear() {
        int currentYear = LocalDate.now().getYear();
        List<Object[]> result = orderRepository.findTotalRevenueAndQuantityByYear(currentYear);
        Map<String, Object> data = new HashMap<>();

        double totalRevenue = 0;
        long totalOrders = 0;  // Chỉ giữ lại số đơn hàng

        if (result != null && !result.isEmpty()) {
            for (Object[] row : result) {
                long orderCount = (row[0] != null) ? ((Number) row[0]).longValue() : 0;
                double revenue = (row[2] != null) ? ((Number) row[2]).doubleValue() : 0;
                Integer voucherId = (row[3] != null) ? ((Number) row[3]).intValue() : null;

                totalOrders += orderCount; // Cộng dồn số đơn hàng

                double discount = 0;
                if (voucherId != null) {
                    try {
                        VoucherDTO voucher = voucherClient.getVoucherById(voucherId);
                        if (voucher != null) {
                            if ("Percentage".equals(voucher.getDiscountType())) {
                                discount = (voucher.getDiscountValue() / 100) * revenue;
                            } else if ("Flat".equals(voucher.getDiscountType())) {
                                discount = voucher.getDiscountValue();
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Không thể lấy voucherId=" + voucherId);
                    }
                }

                totalRevenue += revenue - discount;
            }
        }

        // Chỉ trả về totalOrders và totalRevenue
        data.put("totalOrders", totalOrders);
        data.put("totalRevenue", totalRevenue);

        return data;
    }


    public void updateOrderStatus(int orderId, String status) {
        // Tìm đơn hàng theo ID
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy đơn hàng với ID: " + orderId);
        }

        // Cập nhật trạng thái
        Order order = optionalOrder.get();
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public Order findById(int orderID) {
        return orderRepository.findById(orderID).orElse(null);
    }

    @Override
    public List<Order> findAll() {
//        String jpql = """
//        SELECT o
//        FROM Order o
//        JOIN FETCH o.user u
//        JOIN FETCH o.orderDetails od
//        JOIN FETCH od.product p
//        ORDER BY o.orderDate ASC
//        """;
        String jpql = """
        SELECT o 
        FROM Order o
        JOIN FETCH o.orderDetails od
        ORDER BY o.orderDate ASC
        """;

        // Thực thi truy vấn JPQL
        return entityManager.createQuery(jpql, Order.class).getResultList();
    }

    @Override
    public Map<String, Object> getRevenueStatistics(LocalDate startDate, LocalDate endDate){
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        double totalRevenue = orders.stream()
                .mapToDouble(order -> order.getOrderDetails().stream()
                        .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
                        .sum() + order.getFeeShip())
                .sum();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRevenue", totalRevenue);
        stats.put("totalOrders", orders.size());
        return stats;
    }

    // lấy doanh thu 1 năm và số sản phẩm
//    @Override
//    public Map<String, Object> getRevenueAndQuantityForCurrentYear() {
////        int currentYear = LocalDate.now().getYear();
////        List<Object[]> result = orderRepository.findTotalRevenueAndQuantityByYear(currentYear);
////        Map<String, Object> data = new HashMap<>();
////        if (result != null && !result.isEmpty()) {
////            Object[] row = result.get(0);
////            data.put("totalQuantity", row[0] != null ? row[0] : 0);
////            data.put("totalRevenue", row[1] != null ? row[1] : 0);
////        } else {
////            data.put("totalQuantity", 0);
////            data.put("totalRevenue", 0);
////        }
////        return data;
//        return null;
//    }
//
//    @Override
//    public List<Object[]> getTop10LoyalCustomers(int minOrders) {
//        List<Object[]> loyalCustomers = orderRepository.findLoyalCustomers(minOrders);
//        return loyalCustomers.stream().limit(10).collect(Collectors.toList());
//    }

    @Override
    public Map<String, Long> getOrderStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("Processing", orderRepository.countByStatus("Processing"));
        statistics.put("Shipped", orderRepository.countByStatus("Shipped"));
        statistics.put("Delivered", orderRepository.countByStatus("Delivered"));
        statistics.put("Return", orderRepository.countByStatus("Return"));
        return statistics;
    }



}
