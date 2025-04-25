package com.shoestore.Server.service;

import com.shoestore.Server.dto.response.BestSellerDTO;

import com.shoestore.Server.dto.response.ProductResponseDTO;
import com.shoestore.Server.entities.OrderDetail;
//import com.shoestore.Server.entities.Product;
import com.shoestore.Server.dto.response.OrderDetailResponeDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDetailService {
    public Map<String, Object> fetchOrderDetailByOrderID(int orderID);
//    public List<Product> getTopSellingProducts(LocalDate startDate, LocalDate endDate, int limit);
    public void save(OrderDetail orderDetail);
    public List<BestSellerDTO> getBestSellers(LocalDate startDate, LocalDate endDate, int page, int size);
//    public List<OrderDetail> findByProductIDAndOrderID(int productID, int orderID) ;
//    public Optional<OrderDetail> findByProductIDAndOrderIDDelete(int productID, int orderID) ;
//    public void deleteByProductIDAndOrderID(int productID, int orderID) ;
    public OrderDetail addOrderDetail(OrderDetailResponeDTO dto);
}


