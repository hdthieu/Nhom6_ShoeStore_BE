package com.shoestore.Server.service;


import com.shoestore.Server.dto.response.LoyalCustomerDTO;
import com.shoestore.Server.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Service
public interface OrderService {
    public List<Order> findAll();
    public Map<String, Object> getRevenueStatistics(LocalDate startDate, LocalDate endDate);
    public Map<String, Object> getRevenueAndOrdersForCurrentYear();
    public List<LoyalCustomerDTO> getTop10LoyalCustomers(int minOrders);
    public Map<String, Long> getOrderStatistics();
    public void updateOrderStatus(int orderId, String status) ;
    public Order findById(int orderID);
    public Page<Order> findByStatus(String status, Pageable pageable);
    List<Order> getOrdersByUserId(Integer userId);
    Order addOrder(Order order);
}


