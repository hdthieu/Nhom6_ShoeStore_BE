package com.shoestore.Server.client;

import com.shoestore.Server.dto.response.VoucherResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8765/vouchers")
public interface VoucherClient {
    @GetMapping("/{voucherId}")
    VoucherResponseDTO getVoucherById(@PathVariable("voucherId") Integer voucherId);

    @GetMapping("/voucherloyalcus/{voucherId}")
    VoucherResponseDTO getVoucherLoyalcusById(@PathVariable("voucherId") Integer voucherId);
}
