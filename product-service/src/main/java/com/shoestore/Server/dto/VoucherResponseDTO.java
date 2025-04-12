package com.shoestore.Server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponseDTO {
    private int voucherID;
    private String discountType;
    private double discountValue;


}
