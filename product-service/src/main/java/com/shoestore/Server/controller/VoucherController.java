package com.shoestore.Server.controller;

import com.shoestore.Server.dto.VoucherResponseDTO;
import com.shoestore.Server.entities.Voucher;
import com.shoestore.Server.repositories.VoucherRepository;
import com.shoestore.Server.service.VoucherService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchVouchers(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        LocalDate start = null;
        LocalDate end = null;
        try {
            if (startDate != null && !startDate.isEmpty()) {
                start = LocalDate.parse(startDate);  // Chuyển đổi startDate
            }
            if (endDate != null && !endDate.isEmpty()) {
                end = LocalDate.parse(endDate);  // Chuyển đổi endDate
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi chuyển đổi ngày: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Ngày không hợp lệ"));
        }
        List<Voucher> vouchers = voucherService.findVoucherByCodeOrDate(start, end);

        if (vouchers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "Không có voucher nào trong khoảng thời gian này"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("vouchers", vouchers);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Voucher> addVoucher(@Validated @RequestBody Voucher voucherDTO) {
        Voucher savedVoucher = voucherService.addVoucher(voucherDTO);
        return ResponseEntity.ok(savedVoucher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable int id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucher.isPresent()) {
            return ResponseEntity.ok(voucher.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable int id, @RequestBody Voucher voucher) {
        Voucher updatedVoucher = voucherService.updateVoucher(id, voucher);
        if (updatedVoucher != null) {
            return ResponseEntity.ok(updatedVoucher);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable("id") int id) {
        voucherService.deleteVoucher(id);
        System.out.println("voucher deleted  : ");
        return ResponseEntity.ok("Voucher deleted");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getVouchers(
            @RequestParam(required = false, defaultValue = "all") String status,
            @RequestParam(required = false, defaultValue = "") String search
    ) {
        List<Voucher> vouchers;
        if ("all".equals(status)) {
            vouchers = voucherService.getAllVouchers().stream()
                    .filter(v -> v.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            vouchers = voucherRepository.findByStatusAndNameContainingIgnoreCase(status, search);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("vouchers", vouchers);

        return ResponseEntity.ok(response);
    }

    // Dung Cho LoyalCustomer
    @GetMapping("/voucherloyalcus/{id}")
    public ResponseEntity<VoucherResponseDTO> getVouForLoyalCus(@PathVariable int id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucher.isPresent()) {
            Voucher v = voucher.get();
            VoucherResponseDTO vcDTO = new VoucherResponseDTO(v.getVoucherID(), v.getDiscountType(), v.getDiscountValue());
            return ResponseEntity.ok(vcDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
