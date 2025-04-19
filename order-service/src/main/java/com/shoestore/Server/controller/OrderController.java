package com.shoestore.Server.controller;



import com.shoestore.Server.dto.response.LoyalCustomerDTO;
import com.shoestore.Server.dto.response.OrderDTO;
import com.shoestore.Server.entities.Order;
import com.shoestore.Server.service.OrderService;
import com.shoestore.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api/orders")
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(order));
    }
    @PostMapping("/update-status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody Map<String, Object> payload) {
        System.out.println("Payload received: " + payload);
        try {
            int orderId;
            Object orderIdObj = payload.get("orderId");
            if (orderIdObj instanceof Integer) {
                orderId = (Integer) orderIdObj;
            } else if (orderIdObj instanceof Number) {
                orderId = ((Number) orderIdObj).intValue();
            } else {
                return ResponseEntity.badRequest().body("orderId không hợp lệ!");
            }

            String status = (String) payload.get("status");
            if (status == null || status.isEmpty()) {
                return ResponseEntity.badRequest().body("status không hợp lệ!");
            }

            System.out.println("OrderId: " + orderId + ", Status: " + status);

            // Gọi service để cập nhật
            orderService.updateOrderStatus(orderId, status);

            return ResponseEntity.ok("Cập nhật trạng thái thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật thất bại: " + e.getMessage());
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<Map<String, Object>> getRevenueStatistics(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // Chuyển đổi String thành LocalDate
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Lấy dữ liệu thống kê từ service
        Map<String, Object> stats = orderService.getRevenueStatistics(start, end);
        return ResponseEntity.ok(stats);
    }

    // Tính tổng tiền cho đơn hàng
    private double calculateTotalPrice(Order order) {
        double total = order.getOrderDetails().stream()
                .mapToDouble(od -> od.getQuantity() * od.getPrice())
                .sum();
        return total - order.getFeeShip();
    }


    @GetMapping("/dsachOrders")
    public List<Map<String, Object>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        return orders.stream()
                .map(order -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderID", order.getOrderID());
                    map.put("dateCreated", order.getOrderDate());
                    map.put("totalPrice", calculateTotalPrice(order));
                    map.put("status", order.getStatus());

                    // Gọi sang UserClient để lấy tên user
                    String userName = userService.getUserNameById(order.getUserID());
                    map.put("name", userName);

                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/yearly-revenue")
    public ResponseEntity<Map<String, Object>> getYearlyRevenue() {
        Map<String, Object> data = orderService.getRevenueAndOrdersForCurrentYear();
        return ResponseEntity.ok(data);
    }

    // Danh sách khách hàng thân thiết
    @GetMapping("/loyal-customers")
    public ResponseEntity<List<LoyalCustomerDTO>> getLoyalCustomers(
            @RequestParam(name = "minOrders", defaultValue = "3") int minOrders
    ) {
        List<LoyalCustomerDTO> loyalCustomers = orderService.getTop10LoyalCustomers(minOrders);
        return ResponseEntity.ok(loyalCustomers);
    }

    // handle status order
    @GetMapping("/OrderStatistics")
    public Map<String, Long> getOrderStatistics() {
        return orderService.getOrderStatistics();
    }

    @GetMapping("/searchStatus")
    public ResponseEntity<Page<OrderDTO>> filterOrdersByStatus(@RequestParam String status,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderService.findByStatus(status, pageable);

        List<OrderDTO> dtos = orderPage.getContent().stream()
                .map(order -> {
                    OrderDTO dto = new OrderDTO();
                    dto.setOrderID(order.getOrderID());
                    dto.setDateCreated(order.getOrderDate());
                    dto.setStatus(order.getStatus());

                    // Gọi sang UserService để lấy tên
                    String userName = userService.getUserNameById(order.getUserID());
                    dto.setName(userName);

                    // Tính tổng tiền
                    double totalPrice = calculateTotalPrice(order);
                    dto.setTotalPrice(totalPrice);

                    return dto;
                })
                .collect(Collectors.toList());

        Page<OrderDTO> result = new PageImpl<>(dtos, pageable, orderPage.getTotalElements());
        return ResponseEntity.ok(result);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }



}

