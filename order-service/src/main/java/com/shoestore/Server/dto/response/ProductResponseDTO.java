package com.shoestore.Server.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
        private int productID;
        private String productName;
        private double price;
//        private String brandName;
//        private String categoryName;
        private long totalQuantity;
        private List<String> imgUrl;
    }
