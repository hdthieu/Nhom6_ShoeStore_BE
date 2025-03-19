package com.shoestore.Server.service.impl;

//import com.shoestore.Server.entities.Address;
import com.shoestore.Server.entities.OrderDetail;
//import com.shoestore.Server.entities.Product;
//import com.shoestore.Server.entities.Voucher;
import com.shoestore.Server.repositories.OrderDetailRepository;
//import com.shoestore.Server.repositories.ProductRepository;
import com.shoestore.Server.service.OrderDetailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
//    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

//    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
//        this.orderDetailRepository = orderDetailRepository;
//        this.productRepository = productRepository;
//    }

    public Map<String, Object> fetchOrderDetailByOrderID(Long orderID) {
        // Lệnh Native SQL để lấy thông tin đơn hàng và chi tiết sản phẩm
        String sql = """
        SELECT o.status, o.feeShip, o.shippingAddress, v.discountValue AS voucherDiscount, v.discountType AS voucherType,
               od.quantity, od.price, p.productName, u.name AS userName, u.phoneNumber, a.street AS userAddress, o.orderDate, p.productID
        FROM OrderDetail od
        JOIN Product p ON od.productID = p.productID
        JOIN Orders o ON od.orderID = o.orderID
        LEFT JOIN Voucher v ON o.voucherID = v.voucherID
        LEFT JOIN Users u ON o.userID = u.userID
        LEFT JOIN Address a ON u.userID = a.userID
        WHERE o.orderID = ?1
    """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, orderID);  // Thay ?1 bằng tham số 'orderID'

        // Lấy kết quả từ cơ sở dữ liệu
        List<Object[]> results = query.getResultList();

        // Biến để lưu thông tin trả về
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> orderDetails = new ArrayList<>();
        Map<String, Object> orderInfo = new HashMap<>();

        // Biến để tính toán tổng giá trị sản phẩm
        double totalProductValue = 0.0;

        // Lặp qua kết quả truy vấn để xử lý
        for (Object[] result : results) {
            if (orderInfo.isEmpty()) {
                // Lấy thông tin đơn hàng (chỉ cần lấy 1 lần)
                double voucherDiscount = Optional.ofNullable(result[3]).map(v -> (double) v).orElse(0.0); // Giá trị giảm giá, mặc định 0.0 nếu NULL
                String voucherType = Optional.ofNullable(result[4]).map(v -> (String) v).orElse("fixed"); // Loại giảm giá, mặc định "fixed" nếu NULL
                double feeShip = Optional.ofNullable(result[1]).map(f -> (double) f).orElse(0.0); // Phí vận chuyển, mặc định 0.0 nếu NULL

                // Lưu thông tin đơn hàng vào map
                orderInfo.put("status", result[0]);
                orderInfo.put("feeShip", feeShip);
                orderInfo.put("shippingAddress", result[2]);
                orderInfo.put("voucher", voucherDiscount);
                orderInfo.put("voucherType", voucherType);

            }

            // Lấy thông tin sản phẩm
            int quantity = Optional.ofNullable(result[5]).map(q -> (int) q).orElse(0);
            double price = Optional.ofNullable(result[6]).map(p -> (double) p).orElse(0.0);
            String productName = (String) result[7];
//            String imageUrl = (String) result[8];
            String userName = (String) result[8];
            String phoneNumber = (String) result[9];

            String addressStr = (String) result[10];
            java.sql.Date sqlDate = (java.sql.Date) result[11]; // Cast to java.sql.Date
            int productID = (int) result[12];
            LocalDate orderDate = sqlDate.toLocalDate();

            totalProductValue += quantity * price;
            // Thêm thông tin sản phẩm vào danh sách
            Map<String, Object> detail = new HashMap<>();
            detail.put("productName", productName);
            detail.put("quantity", quantity);
            detail.put("price", price);
            detail.put("productID", productID);
//            detail.put("imageURL", imageUrl);
            orderDetails.add(detail);
            response.put("userName", userName);
            response.put("phoneNumber", phoneNumber);
            response.put("address", addressStr);
            response.put("orderDate", orderDate);
        }

        // Tính toán tổng tiền
        double voucherDiscount = Optional.ofNullable(orderInfo.get("voucher")).map(v -> (double) v).orElse(0.0); // Giá trị giảm giá
        String voucherType = Optional.ofNullable(orderInfo.get("voucherType")).map(v -> (String) v).orElse("fixed"); // Loại giảm giá
        double feeShip = Optional.ofNullable(orderInfo.get("feeShip")).map(f -> (double) f).orElse(0.0); // Phí vận chuyển

        // Biến để tính số tiền giảm giá
        double discountAmount = 0.0;
        if (voucherDiscount > 0) {
            // Tính số tiền giảm giá
            if ("percentage".equalsIgnoreCase(voucherType)) {
                discountAmount = totalProductValue * (voucherDiscount / 100);
            } else {
                discountAmount = voucherDiscount;
            }
        } else {
            discountAmount = 0.0;
        }

        double totalAmount = totalProductValue - discountAmount + feeShip;

        response.put("orderID", orderID);
        response.put("orderDetails", orderDetails);
        response.putAll(orderInfo);
        response.put("totalAmount", totalAmount);

        // Trả về kết quả
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