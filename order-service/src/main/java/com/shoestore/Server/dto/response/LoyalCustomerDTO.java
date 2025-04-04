package com.shoestore.Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoyalCustomerDTO {
    private int userID;
    private String Name;
    private double totalSpent;
    private int totalOrder;
    private String email;
//    private String phoneNumber;
}
