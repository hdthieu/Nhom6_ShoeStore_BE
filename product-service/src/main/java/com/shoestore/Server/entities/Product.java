package com.shoestore.Server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private int productID;
    @Column(name = "productName")
    private String productName;
    @ElementCollection
    @CollectionTable(name = "Product_ImageURL", joinColumns = @JoinColumn(name = "productID"))
    @Column(name = "imageURL")
    private List<String> imageURL;
    private String description;
    private double price;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brandID")
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryID")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplierID")
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference("productDetailsReference")
    private List<ProductDetail> productDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    public Product() {

    }
    public Product(int productID, String productName, List<String> imageURL, String description, double price, String status, Brand brand, Supplier supplier, Category category, LocalDateTime createDate) {
        this.productID = productID;
        this.productName = productName;
        this.imageURL = imageURL;
        this.description = description;
        this.price = price;
        this.status = status;
        this.brand = brand;
        this.supplier = supplier;
        this.category = category;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
