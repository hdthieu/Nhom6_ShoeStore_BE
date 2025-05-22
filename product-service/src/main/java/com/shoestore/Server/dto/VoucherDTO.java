package com.shoestore.Server.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class VoucherDTO {

    private int voucherID;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String discountType;
    private double discountValue;
    private String status;
    private double minValueOrder;
}
