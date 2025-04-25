package com.shoestore.Server.controller;

import com.shoestore.Server.client.ProductClient;
import com.shoestore.Server.dto.response.BestSellerDTO;
import com.shoestore.Server.dto.response.ProductResponseDTO;
import com.shoestore.Server.entities.Order;
import com.shoestore.Server.entities.OrderDetail;
import com.shoestore.Server.repositories.OrderDetailRepository;
import com.shoestore.Server.repositories.OrderRepository;
import com.shoestore.Server.request.*;
import com.shoestore.Server.service.OrderDetailService;
import com.shoestore.Server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/OrderDetail/layTT/{orderID}")
    public ResponseEntity<Map<String, Object>> getOrderDetailByOrderID(@PathVariable int orderID) {
        Map<String, Object> orderDetail = orderDetailService.fetchOrderDetailByOrderID(orderID);
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/bestsellers")
    public ResponseEntity<List<BestSellerDTO>> getBestSellers(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        if (type != null) {
            LocalDate now = LocalDate.now();
            switch (type) {
                case "day" -> {
                    startDate = now;
                    endDate = now;
                }
                case "week" -> {
                    startDate = now.minusDays(6);
                    endDate = now;
                }
                case "month" -> {
                    startDate = now.withDayOfMonth(1);
                    endDate = now;
                }
                case "year" -> {
                    startDate = now.withDayOfYear(1);
                    endDate = now;
                }
            }
        }

        List<BestSellerDTO> bestSellers = orderDetailService.getBestSellers(startDate, endDate, page, size);
        return ResponseEntity.ok(bestSellers);
    }

    @PostMapping("/OrderDetail/add")
    public ResponseEntity<?> addOrderDetail(@RequestBody OrderDetailRequestDTO dto) {
        try {
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + dto.getOrderId()));

            // Kiểm tra productDetailId có tồn tại không thông qua product-service
            ProductResponseDTO productResponseDTO = productClient.getProductById(dto.getProductDetailId());
            if (productResponseDTO == null) {
                throw new RuntimeException("Product detail not found with ID: " + dto.getProductDetailId());
            }

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProductDetail(dto.getProductDetailId()); // Gán ID trực tiếp
            orderDetail.setQuantity(dto.getQuantity());
            orderDetail.setPrice(dto.getPrice());

            orderDetailRepository.save(orderDetail);
            return ResponseEntity.ok("Added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed: " + e.getMessage());
        }
    }
}
