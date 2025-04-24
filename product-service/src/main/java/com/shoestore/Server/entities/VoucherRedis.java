package com.shoestore.Server.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;
import java.time.LocalDate;

@RedisHash("Voucher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherRedis implements Serializable {
    @Id
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