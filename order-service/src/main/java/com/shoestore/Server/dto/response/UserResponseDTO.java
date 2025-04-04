package com.shoestore.Server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private int userID;
    private String name;
    private String email;
    private String phone;
//    public UserResponseDTO(int userID, String name, String email) {
//        this.userID = userID;
//        this.name = name;
//        this.email = email;
//    }
}