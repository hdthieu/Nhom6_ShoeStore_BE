package com.shoestore.Server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;
    private String name;
    private String email;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Address> addresses;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    private String password;
    @Column(name = "userName")
    private String userName;
    private String CI;
    private String status;
    @ManyToOne
    @JoinColumn(name = "roleID")
    private Role role;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cart cart;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private List<Order> orders;
        private int orderID;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private List<Review> reviews;
}
