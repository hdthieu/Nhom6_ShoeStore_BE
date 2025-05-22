package com.shoestore.Server.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("productCache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int productID;
    private String productName;
    private List<String> imageURL;
    private String description;
    private double price;
    private String status;
    private String brandName;
    private String categoryName;
    private String supplierName;
}
