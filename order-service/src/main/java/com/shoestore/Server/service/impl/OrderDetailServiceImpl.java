package com.shoestore.Server.service.impl;

//import com.shoestore.Server.entities.Address;
import com.shoestore.Server.client.ProductClient;
import com.shoestore.Server.client.UserClient;

import com.shoestore.Server.dto.response.*;
import com.shoestore.Server.dto.response.ProductResponseDTO;
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

import java.time.LocalDate;
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
        // Lấy thông tin đơn hàng
        Order order = orderRepository.findByOrderID(orderID);
        List<OrderDetail> details = orderDetailRepository.findByOrder(order);

        // Lấy thông tin người dùng
        Map<String, String> user = new HashMap<>();
        List<UserResponseDTO> userResponseList = userClient.getListCusCustomByID(order.getUserID());
        if (!userResponseList.isEmpty()) {
            UserResponseDTO userResponse = userResponseList.get(0);
            user.put("name", userResponse.getName());
            user.put("email", userResponse.getEmail());
            user.put("phoneNumber", userResponse.getPhoneNumber());
        }

        // Lấy thông tin voucher
        Map<String, Object> voucher = null;
        if (order.getVoucherID() != 0) {
            VoucherResponseDTO voucherResponse = productClient.getVoucherById(order.getVoucherID());
            voucher = new HashMap<>();
            voucher.put("voucherID", voucherResponse.getId());
            voucher.put("discountValue", voucherResponse.getDiscountValue());
            voucher.put("discountType", voucherResponse.getDiscountType());
        }

        // Tính toán tổng giá trị đơn hàng
        double total = 0;
        List<Map<String, Object>> orderDetails = new ArrayList<>();
        for (OrderDetail d : details) {
            ProductResponseDTO product = productClient.getProductById(d.getProductDetail());
            double subtotal = d.getQuantity() * d.getPrice();
            total += subtotal;

            // Lấy danh sách productDetails để lấy màu sắc và kích thước
            List<ProductDetailDTO> productDetails = product.getProductDetails();

            // Thêm thông tin chi tiết sản phẩm vào detailMap
            for (ProductDetailDTO productDetail : productDetails) {
                Map<String, Object> detailMap = new HashMap<>();
                detailMap.put("productID", d.getProductDetail());
                detailMap.put("productName", product.getProductName());
                detailMap.put("quantity", d.getQuantity());
                detailMap.put("price", d.getPrice());

                // Thêm thông tin về màu sắc và kích thước
                detailMap.put("color", productDetail.getColor());
                detailMap.put("size", productDetail.getSize());

                // Lấy hình ảnh của sản phẩm
                List<String> imgList = product.getImgUrl();
                String firstImg = (imgList != null && !imgList.isEmpty()) ? imgList.get(0) : "";
                detailMap.put("imgUrl", firstImg);

                // Thêm detailMap vào danh sách orderDetails
                orderDetails.add(detailMap);

                // In thông tin để kiểm tra
                System.out.println("Product name: " + product.getProductName());
                System.out.println("Quantity: " + d.getQuantity());
                System.out.println("Price: " + d.getPrice());
                System.out.println("Color: " + productDetail.getColor());
                System.out.println("Size: " + productDetail.getSize());
                System.out.println("Subtotal: " + d.getQuantity() * d.getPrice());
            }
        }

        // Tính toán giảm giá nếu có
        double discount = 0;
        if (voucher != null) {
            if ("Percentage".equalsIgnoreCase((String) voucher.get("discountType"))) {
                discount = total * (double) voucher.get("discountValue") / 100;
            } else {
                discount = (double) voucher.get("discountValue");
            }
        }

        // Tính toán tổng tiền thanh toán
        double totalAmount = total - discount + order.getFeeShip();

//        // Trả về thông tin đơn hàng
//        return Map.of(
//                "orderID", orderID,
//                "status", order.getStatus(),
//                "orderDate", order.getOrderDate(),
//                "feeShip", order.getFeeShip(),
//                "shippingAddress", order.getShippingAddress(),
//                "user", user,
//                "voucher", voucher,
//                "orderDetails", orderDetails,
//                "totalAmount", totalAmount
//        );
        Map<String, Object> response = new HashMap<>();
        response.put("orderID", orderID);
        response.put("status", order.getStatus());
        response.put("orderDate", order.getOrderDate());
        response.put("feeShip", order.getFeeShip());
        response.put("shippingAddress", order.getShippingAddress());
        response.put("user", user);
        response.put("voucher", voucher);
        response.put("orderDetails", orderDetails);
        response.put("totalAmount", totalAmount);
        return response;

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
    public List<BestSellerDTO> getBestSellers(LocalDate startDate, LocalDate endDate, int page, int size) {
        // Cộng thêm 1 ngày cho endDate để lấy đến hết ngày được chọn
        LocalDate adjustedEndDate = endDate.plusDays(1);
        int offset = page * size;

        List<Object[]> topSellers = orderDetailRepository.getTopSellingProductsByDate(startDate, adjustedEndDate, size, offset);
        List<BestSellerDTO> result = new ArrayList<>();

        for (Object[] row : topSellers) {
            int productID = ((Number) row[0]).intValue();
            long totalSold = ((Number) row[1]).longValue();

            ProductResponseDTO product = productClient.getProductById(productID);
            if (product != null) {
                result.add(new BestSellerDTO(
                        productID,
                        totalSold,
                        product.getProductName(),
                        product.getBrand().getName(),
                        product.getCategory().getName()
                ));
            }
        }

        return result;
    }


//    @Override
//    public List<BestSellerDTO> getBestSellers(int page, int size) {
//        int offset = page * size;
//        List<Object[]> topSellers = orderDetailRepository.getTopSellingProducts(size, offset);
//        List<BestSellerDTO> result = new ArrayList<>();
//
//        for (Object[] row : topSellers) {
//            int productID = ((Number) row[0]).intValue();
//            long totalSold = ((Number) row[1]).longValue();
//
//            ProductResponseDTO product = productClient.getProductById(productID);
//
//            if (product != null) {
//                result.add(new BestSellerDTO(
//                        productID,
//                        totalSold,
//                        product.getProductName(),
//                        product.getBrand().getName(),
//                        product.getCategory().getName()
//                ));
//            }
//        }
//
//        return result;
//    }
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
@Override
public OrderDetail addOrderDetail(OrderDetailResponeDTO dto) {
    Order order = orderRepository.findById((int) dto.getOrderID())
            .orElseThrow(() -> new RuntimeException("Order not found"));

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setOrder(order);
    orderDetail.setProductDetail(dto.getProductDetail());
    orderDetail.setPrice(dto.getPrice());
    orderDetail.setQuantity(dto.getQuantity());

    return orderDetailRepository.save(orderDetail);
}
}