package com.shoestore.Server.client;

import com.shoestore.Server.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "order-service", url = "http://localhost:8765/Order")
//@FeignClient(name = "order-service", url = "http://api-gateway:8765/Order")

public interface OrderClient {
    @GetMapping("/{id}")
    OrderDTO getOrderById(@PathVariable("id") int id);
    @GetMapping("/Order/OrderDetail/layTT/{orderID}")
    Map<String, Object> getOrderDetailByOrderID(@PathVariable("orderID") int orderID);
    @PutMapping("/updateStatus/{id}")
    void updateOrderStatus(@PathVariable("id") int id, @RequestParam("status") String status);
    @GetMapping("/user/{userId}")
    List<OrderDTO> getOrdersByUserId(@PathVariable("userId") int userId);
}


