package com.shoestore.Server.service.impl;

//import com.shoestore.Server.entities.Address;
import com.shoestore.Server.client.ProductClient;
import com.shoestore.Server.client.UserClient;

import com.shoestore.Server.dto.response.ProductResponseDTO;
import com.shoestore.Server.dto.response.UserResponseDTO;
import com.shoestore.Server.dto.response.VoucherResponseDTO;
import com.shoestore.Server.entities.Order;
import com.shoestore.Server.entities.OrderDetail;
//import com.shoestore.Server.entities.Product;
//import com.shoestore.Server.entities.Voucher;
import com.shoestore.Server.repositories.OrderDetailRepository;
//import com.shoestore.Server.repositories.ProductRepository;
import com.shoestore.Server.repositories.OrderRepository;
import com.shoestore.Server.service.OrderDetailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    //    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, UserClient userClient, ProductClient productClient) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
    }


    public Map<String, Object> fetchOrderDetailByOrderID(int orderID) {
        Order order = orderRepository.findByOrderID(orderID);
        List<OrderDetail> details = orderDetailRepository.findByOrder(order);
        Map<String, String> user = new HashMap<>();
        List<UserResponseDTO> userResponseList = userClient.getListCusCustomByID(order.getUserID());
        if (!userResponseList.isEmpty()) {
            UserResponseDTO userResponse = userResponseList.get(0);
            user.put("name", userResponse.getName());
            user.put("email", userResponse.getEmail());
            user.put("phoneNumber", userResponse.getPhoneNumber());
        }
        Map<String, Object> voucher = null;
        if (order.getVoucherID() != 0) {
            VoucherResponseDTO voucherResponse = productClient.getVoucherById(order.getVoucherID());
            voucher = new HashMap<>();
            voucher.put("voucherID", voucherResponse.getId());
            voucher.put("discountValue", voucherResponse.getDiscountValue());
            voucher.put("discountType", voucherResponse.getDiscountType());
        }
        double total = 0;
        List<Map<String, Object>> orderDetails = new ArrayList<>();
        for (OrderDetail d : details) {
            ProductResponseDTO product = productClient.getProductById(d.getProductDetail());
            double subtotal = d.getQuantity() * d.getPrice();
            total += subtotal;
            System.out.println("Product: " + product);
            System.out.println("Image URL: " + product.getImgUrl());

            Map<String, Object> detailMap = new HashMap<>();
            detailMap.put("productID", d.getProductDetail());
            detailMap.put("productName", product.getProductName());
            detailMap.put("quantity", d.getQuantity());
            detailMap.put("price", d.getPrice());

            List<String> imgList = product.getImgUrl();
            String firstImg = (imgList != null && !imgList.isEmpty()) ? imgList.get(0) : "";
            detailMap.put("imgUrl", firstImg);

            orderDetails.add(detailMap);


        }

        double discount = 0;
        if (voucher != null) {
            if ("Percentage".equalsIgnoreCase((String) voucher.get("discountType"))) {
                discount = total * (double) voucher.get("discountValue") / 100;
            } else {
                discount = (double) voucher.get("discountValue");
            }
        }

        double totalAmount = total - discount + order.getFeeShip();

        return Map.of(
                "orderID", orderID,
                "status", order.getStatus(),
                "orderDate", order.getOrderDate(),
                "feeShip", order.getFeeShip(),
                "shippingAddress", order.getShippingAddress(),
                "user", user,
                "voucher", voucher,
                "orderDetails", orderDetails,
                "totalAmount", totalAmount
        );
    }





//    @Override
//    public List<Product> getTopSellingProducts(LocalDate startDate, LocalDate endDate, int limit) {
//        List<Object[]> results = orderDetailRepository.findTopSellingProducts(startDate, endDate);
//
//        // Chỉ lấy sản phẩm bán chạy nhất (dưới hạn số lượng sản phẩm)
//        return results.stream()
//                .limit(limit)
//                .map(obj -> (Product) obj[0])
//                .collect(Collectors.toList());
//    }


    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

//    @Override
//    public List<OrderDetail> findByProductIDAndOrderID(int productID, int orderID) {
//        return orderDetailRepository.findByProductIDAndOrderID(productID, orderID);
//    }

//    @Override
//    public Optional<OrderDetail> findByProductIDAndOrderIDDelete(int productID, int orderID) {
//        return orderDetailRepository.findByProductProductIDAndOrderOrderID(productID, orderID);
//    }
//
//    @Transactional
//    @Override
//    public void deleteByProductIDAndOrderID(int productID, int orderID) {
//        orderDetailRepository.deleteByProductProductIDAndOrderOrderID(productID, orderID);
//    }
}