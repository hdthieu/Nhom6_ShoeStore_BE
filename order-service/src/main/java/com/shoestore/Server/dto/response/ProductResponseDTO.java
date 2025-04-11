package com.shoestore.Server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
        @JsonProperty("imageURL")
        private List<String> imgUrl;
        private BrandDTO brand;
        private CategoryDTO category;

}
