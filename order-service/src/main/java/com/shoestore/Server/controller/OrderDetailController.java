package com.shoestore.Server.controller;


import com.shoestore.Server.service.OrderDetailService;
import com.shoestore.Server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;
//    @Autowired
//    private ProductService productService;

//    @Autowired
//    private ProductDetailService productDetailService;


//    @GetMapping("/top-selling")
//    public ResponseEntity<List<Product>> getTopSellingProducts(
//            @RequestParam String type,
//            @RequestParam(required = false, defaultValue = "5") int limit) {
//        LocalDate startDate;
//        LocalDate endDate = LocalDate.now();
//        switch (type) {
//            case "day":
//                startDate = endDate.minusDays(1);
//                break;
//            case "week":
//                startDate = endDate.minusWeeks(1);
//                break;
//            case "month":
//                startDate = endDate.minusMonths(1);
//                break;
//            case "year":
//                startDate = endDate.minusYears(1);
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid type: " + type);
//        }
//        List<Product> products = orderDetailService.getTopSellingProducts(startDate, endDate, limit);
//        return ResponseEntity.ok(products);
//    }
//
    // API lấy thông tin chi tiết đơn hàng theo orderID
    @GetMapping("/OrderDetail/layTT/{orderID}")
    public ResponseEntity<Map<String, Object>> getOrderDetailByOrderID(@PathVariable int orderID) {
        Map<String, Object> orderDetail = orderDetailService.fetchOrderDetailByOrderID(orderID);
        return ResponseEntity.ok(orderDetail);
    }

//    @PostMapping("/addProductToOrder")
//    public ResponseEntity<?> addProductToOrder(@RequestBody Map<String, Object> requestData) {
//        try {
//            int orderID = Integer.parseInt(requestData.get("orderID").toString());
//            int productID = Integer.parseInt(requestData.get("productID").toString());
//            int quantity = Integer.parseInt(requestData.get("quantity").toString());
//            double price = Double.parseDouble(requestData.get("price").toString());
//
//            // Lấy thông tin sản phẩm
//            List<Product> products = productService.getById(productID);
//            if (products == null || products.isEmpty()) {
//                Map<String, Object> response = new HashMap<>();
//                response.put("message", "Sản phẩm không tồn tại.");
//                response.put("success", false);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//            }
//
//            Product product = products.get(0);
//
//            // Lấy danh sách ProductDetail liên quan đến sản phẩm
//            List<ProductDetail> productDetails = product.getProductDetails();
//            if (productDetails == null || productDetails.isEmpty()) {
//                Map<String, Object> response = new HashMap<>();
//                response.put("message", "Không tìm thấy thông tin chi tiết sản phẩm.");
//                response.put("success", false);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//            }
//
}