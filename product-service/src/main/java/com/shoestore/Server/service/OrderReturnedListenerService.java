package com.shoestore.Server.service;

import com.shoestore.Server.config.RabbitMQConfig;
import com.shoestore.Server.dto.OrderReturnedEvent;
import com.shoestore.Server.repositories.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderReturnedListenerService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleOrderReturned(OrderReturnedEvent event) {
        // toàn bộ xử lý nằm trong transaction → session mở
        for (OrderReturnedEvent.ProductQuantity pq : event.getItems()) {
            productRepository.findById(pq.getProductId()).ifPresent(product -> {
                product.getProductDetails().forEach(productDetail -> {
                    if (productDetail.getColor() == pq.getColor() && productDetail.getSize() == pq.getSize()) {
                        System.out.println("check value =  " + productDetail.getColor() + " " + productDetail.getSize());
                        System.out.println("check value pg=  " + pq.getColor() + " " +pq.getSize());
                        int newQuantity = productDetail.getStockQuantity() + pq.getQuantity();
                        productDetail.setStockQuantity(newQuantity);
                    }
                });
                productRepository.save(product);
            });
        }
    }
}
