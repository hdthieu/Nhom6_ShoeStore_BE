package com.shoestore.Server.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private int addressID;
    private String street;
    private String city;
    private String ward;
    private String district;
}
