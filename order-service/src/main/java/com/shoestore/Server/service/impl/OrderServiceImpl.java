package com.shoestore.Server.service.impl;

import com.shoestore.Server.client.ProductClient;
import com.shoestore.Server.client.UserClient;
import com.shoestore.Server.config.RabbitMQConfig;
import com.shoestore.Server.dto.OrderReturnedEvent;
import com.shoestore.Server.dto.response.*;
import com.shoestore.Server.entities.Order;
import com.shoestore.Server.entities.OrderDetail;
import com.shoestore.Server.repositories.OrderRepository;
import com.shoestore.Server.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    public OrderServiceImpl(OrderRepository orderRepository, UserClient userClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
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
                        VoucherResponseDTO voucher = productClient.getVoucherById(voucherId);
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
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy đơn hàng.");
        }

        Order order = optionalOrder.get();
        order.setStatus(status);
        orderRepository.save(order);

        // Nếu trạng thái là "Return", gửi sự kiện trả lại
        if ("Return".equalsIgnoreCase(status)) {
            OrderReturnedEvent event = new OrderReturnedEvent();
            event.setOrderId(orderId);

            List<OrderReturnedEvent.ProductQuantity> items = order.getOrderDetails().stream()
                    .map(detail -> {
                        OrderReturnedEvent.ProductQuantity pq = new OrderReturnedEvent.ProductQuantity();
                        pq.setProductId(detail.getProductDetail()); // Set product ID
                        pq.setQuantity(detail.getQuantity()); // Set quantity

                        // Lấy thông tin sản phẩm từ productClient
                        ProductResponseDTO product = productClient.getProductById(detail.getProductDetail()); // Lấy thông tin sản phẩm

                        // Lấy danh sách ProductDetailDTO từ ProductResponseDTO
                        List<ProductDetailDTO> productDetails = product.getProductDetails(); // Lấy danh sách ProductDetail

                        // Lặp qua ProductDetailDTO để lấy màu sắc và kích thước
                        for (ProductDetailDTO productDetail : productDetails) {
                            // Thêm màu sắc và kích thước vào ProductQuantity
                            pq.setColor(productDetail.getColor()); // Set color
                            pq.setSize(productDetail.getSize());   // Set size
                        }

                        return pq;
                    }).collect(Collectors.toList());

            event.setItems(items);

            // Gửi sự kiện trả lại đơn hàng qua RabbitMQ
            System.out.println("check RabbitMQ for orderId: " + orderId);
            System.out.println("Sending order returned event to RabbitMQ for orderId: " + orderId);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
        }
    }

//    public void updateOrderStatus(int orderId, String status) {
//        // Tìm đơn hàng theo ID
//        Optional<Order> optionalOrder = orderRepository.findById(orderId);
//        if (optionalOrder.isEmpty()) {
//            throw new IllegalArgumentException("Không tìm thấy đơn hàng với ID: " + orderId);
//        }
//
//        // Cập nhật trạng thái
//        Order order = optionalOrder.get();
//        order.setStatus(status);
//        orderRepository.save(order);
//    }

    @Override
    public Order findById(int orderID) {
        return orderRepository.findById(orderID).orElse(null);
    }

    @Override
    public List<Order> findAll() {
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
@Override
public List<LoyalCustomerDTO> getTop10LoyalCustomers(int minOrders) {
    List<UserResponseDTO> customers = userClient.getListCusCustom();
    List<LoyalCustomerDTO> loyalCustomers = new ArrayList<>();

    for (UserResponseDTO user : customers) {
        // Lấy tất cả đơn hàng của user từ DB
        List<Order> orders = orderRepository.findAllByUserID(user.getUserID());

        if (orders.size() >= minOrders) {
            double totalSpent = 0;
            int totalOrder = orders.size(); // Tính tổng số đơn hàng

            for (Order order : orders) {
                double orderTotal = 0;

                for (OrderDetail detail : order.getOrderDetails()) {
                    orderTotal += detail.getQuantity() * detail.getPrice();
                }

                // Gọi voucher-service nếu có voucher
                if (order.getVoucherID() != 0) {
                    VoucherResponseDTO voucher = productClient.getVoucherById(order.getVoucherID());
                    if ("Percentage".equalsIgnoreCase(voucher.getDiscountType())) {
                        orderTotal -= orderTotal * voucher.getDiscountValue() / 100;
                    } else if ("Flat".equalsIgnoreCase(voucher.getDiscountType())) {
                        orderTotal -= voucher.getDiscountValue();
                    }
                }

                totalSpent += orderTotal;
            }

            // Thêm totalOrder vào trong đối tượng LoyalCustomerDTO
            loyalCustomers.add(new LoyalCustomerDTO(user.getUserID(), user.getName(), totalSpent, totalOrder,user.getEmail()));
        }
    }

    // Sắp xếp theo totalSpent giảm dần
    loyalCustomers.sort(Comparator.comparingDouble(LoyalCustomerDTO::getTotalSpent).reversed());

    return loyalCustomers;
}

    @Override
    public Map<String, Long> getOrderStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("Processing", orderRepository.countByStatus("Processing"));
        statistics.put("Shipped", orderRepository.countByStatus("Shipped"));
        statistics.put("Delivered", orderRepository.countByStatus("Delivered"));
        statistics.put("Return", orderRepository.countByStatus("Return"));
        return statistics;
    }
    @Override
    public Page<Order> findByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatusIgnoreCase(status, pageable);
    }



}
