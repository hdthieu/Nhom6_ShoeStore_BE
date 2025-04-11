package com.shoestore.Server.service.impl;

import com.shoestore.Server.entities.Voucher;
import com.shoestore.Server.repositories.VoucherRepository;
import com.shoestore.Server.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<Voucher> findVoucherByCodeOrDate(LocalDate startDate, LocalDate endDate) {
        // Nếu cả hai ngày đều null, trả về tất cả các voucher từ cơ sở dữ liệu
        if (startDate == null && endDate == null) {
            return voucherRepository.findAll();  // Trả về tất cả voucher từ cơ sở dữ liệu
        }

        // Nếu có ngày bắt đầu và ngày kết thúc, tìm các voucher trong phạm vi ngày
        return voucherRepository.findByStartDateBeforeAndEndDateAfter(startDate, endDate);
    }

    @Override
    public Voucher updateVoucher(int id, Voucher voucher) {
        validateVoucherInput(voucher);
        Optional<Voucher> existingVoucher = voucherRepository.findById(id);
        if (existingVoucher.isPresent()) {
            Voucher entityVoucher = existingVoucher.get();
            entityVoucher.setName(voucher.getName());
            entityVoucher.setDescription(voucher.getDescription());
            entityVoucher.setStartDate(voucher.getStartDate());
            entityVoucher.setEndDate(voucher.getEndDate());
            entityVoucher.setDiscountType(voucher.getDiscountType());
            entityVoucher.setDiscountValue(voucher.getDiscountValue());
            entityVoucher.setMinValueOrder(voucher.getMinValueOrder());
            LocalDate today = LocalDate.now();
            if (voucher.getStartDate().isAfter(today)) {
                entityVoucher.setStatus("Upcoming");
            } else if (!voucher.getStartDate().isAfter(today) && !voucher.getEndDate().isBefore(today)) {
                entityVoucher.setStatus("Active");
            } else {
                entityVoucher.setStatus("Expired");
            }

            return voucherRepository.save(entityVoucher);
        } else {
            throw new NoSuchElementException("Không tìm thấy voucher với ID: " + id);
        }
    }


//    @Override
//    public List<Voucher> findVoucherByNameOrCode(String keyword) {
//        return List.of();
//    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher addVoucher(Voucher voucher) {
        validateVoucherInput(voucher); // Gọi hàm kiểm tra

        Voucher entityVoucher = new Voucher();
        entityVoucher.setName(voucher.getName());
        entityVoucher.setDescription(voucher.getDescription());
        entityVoucher.setStartDate(voucher.getStartDate());
        entityVoucher.setEndDate(voucher.getEndDate());
        entityVoucher.setDiscountType(voucher.getDiscountType());
        entityVoucher.setDiscountValue(voucher.getDiscountValue());
        entityVoucher.setMinValueOrder(voucher.getMinValueOrder());

        LocalDate today = LocalDate.now();
        if (voucher.getStartDate().isAfter(today)) {
            entityVoucher.setStatus("Upcoming");
        } else if (!voucher.getStartDate().isAfter(today) && !voucher.getEndDate().isBefore(today)) {
            entityVoucher.setStatus("Active");
        } else {
            entityVoucher.setStatus("Expired");
        }

        return voucherRepository.save(entityVoucher);
    }


    @Override
    public void deleteVoucher(int voucherID) {
        voucherRepository.deleteById(voucherID);  // Xóa khuyến mãi theo voucherID
    }

    private void validateVoucherInput(Voucher voucher) {
        if (voucher.getName() == null || voucher.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên voucher không được để trống");
        }

        if (voucher.getDescription() == null || voucher.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Mô tả không được để trống");
        }

        if (voucher.getDiscountType() == null) {
            throw new IllegalArgumentException("Vui lòng chọn kiểu giảm giá");
        }

        double discountValue = voucher.getDiscountValue();
        if (discountValue <= 0) {
            throw new IllegalArgumentException("Giá trị giảm giá phải lớn hơn 0");
        }

        if ("Percentage".equalsIgnoreCase(voucher.getDiscountType())) {
            if (discountValue > 100) {
                throw new IllegalArgumentException("Giảm giá phần trăm không được vượt quá 100%");
            }
        }

        if (voucher.getMinValueOrder() < 0) {
            throw new IllegalArgumentException("Giá trị đơn hàng tối thiểu không được âm");
        }

        if (voucher.getStartDate() == null || voucher.getEndDate() == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và kết thúc không được để trống");
        }

        if (voucher.getEndDate().isBefore(voucher.getStartDate())) {
            throw new IllegalArgumentException("Ngày kết thúc không được nhỏ hơn ngày bắt đầu");
        }

        if (voucher.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày kết thúc không được nhỏ hơn ngày hiện tại");
        }
    }







}
