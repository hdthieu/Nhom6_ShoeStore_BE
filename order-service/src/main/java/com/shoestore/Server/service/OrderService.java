package com.shoestore.Server.service;


import com.shoestore.Server.entities.Order;
import com.shoestore.Server.entities.OrderDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {
    public List<Order> findAll();
    public Map<String, Object> getRevenueStatistics(LocalDate startDate, LocalDate endDate);
    public Map<String, Object> getRevenueAndOrdersForCurrentYear();
//    public List<Object[]> getTop10LoyalCustomers(int minOrders);
    public Map<String, Long> getOrderStatistics();
    public void updateOrderStatus(int orderId, String status) ;
    public Order findById(int orderID);

}


