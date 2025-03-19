package com.shoestore.Server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  private int productID;
  private String productName;
  private double price;
  private String status;
  private String description;
  private String brandName;
  private String categoryName;
  private LocalDateTime createDate;
  private long totalQuantity;
}

