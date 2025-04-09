package com.shoestore.Server.dto;

import com.shoestore.Server.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private int userID;
    private String name;
    private String email;
    private String phoneNumber;
    private List<AddressDTO> addresses;


    public UserResponseDTO(int userID, String name, String email, String phoneNumber, List<AddressDTO> addresses) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addresses = addresses;
    }
}

