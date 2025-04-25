package com.shoestore.Server.service.impl;

import com.shoestore.Server.entities.Voucher;
import com.shoestore.Server.entities.VoucherRedis;
import com.shoestore.Server.repositories.VoucherRedisRepository;
import com.shoestore.Server.repositories.VoucherRepository;
import com.shoestore.Server.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherRedisRepository voucherRedisRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public VoucherServiceImpl(
            VoucherRepository voucherRepository,
            VoucherRedisRepository voucherRedisRepository,
            RedisTemplate<String, Object> redisTemplate
    ) {
        this.voucherRepository = voucherRepository;
        this.voucherRedisRepository = voucherRedisRepository;
        this.redisTemplate = redisTemplate;
    }

//    @Override
//    public List<Voucher> findVoucherByCodeOrDate(LocalDate startDate, LocalDate endDate) {
//        String cacheKey;
//        if (startDate == null && endDate == null) {
//            cacheKey = "voucher:all";
//        } else {
//            cacheKey = "voucher:filter:" + startDate + ":" + endDate;
//        }
//
//        // Thử lấy từ cache
//        List<Voucher> cached = (List<Voucher>) redisTemplate.opsForValue().get(cacheKey);
//        if (cached != null) {
//            return cached;
//        }
//
//        // Nếu không có thì truy vấn từ DB
//        List<Voucher> result;
//        if (startDate == null && endDate == null) {
//            result = voucherRepository.findAll();
//        } else {
//
//            result = voucherRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);
//        }
//
//        redisTemplate.opsForValue().set(cacheKey, result);
//
//        return result;
//    }
@Override
public List<Voucher> searchVouchersWithFilters(LocalDate startDate, LocalDate endDate, String status) {
    // Tạo key cache dựa trên bộ lọc
    String cacheKey = String.format("voucher:filter:start:%s:end:%s:status:%s",
            startDate != null ? startDate.toString() : "null",
            endDate != null ? endDate.toString() : "null",
            status != null && !status.isEmpty() ? status.toLowerCase() : "all");

    List<Voucher> cached = (List<Voucher>) redisTemplate.opsForValue().get(cacheKey);
    if (cached != null) {
        return cached;
    }
    List<Voucher> all = voucherRepository.findAll();

    List<Voucher> result = all.stream()
            .filter(v -> startDate == null || !v.getStartDate().isBefore(startDate))
            .filter(v -> endDate == null || !v.getEndDate().isAfter(endDate))
            .filter(v -> status == null || status.isEmpty() || v.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());

    // Lưu vào cache
    redisTemplate.opsForValue().set(cacheKey, result, 10, TimeUnit.MINUTES);

    return result;
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
            // Update Redis
            VoucherRedis redis = new VoucherRedis(
                    entityVoucher.getVoucherID(), entityVoucher.getName(), entityVoucher.getDescription(),
                    entityVoucher.getStartDate(), entityVoucher.getEndDate(), entityVoucher.getDiscountType(),
                    entityVoucher.getDiscountValue(), entityVoucher.getStatus(), entityVoucher.getMinValueOrder()
            );
            voucherRedisRepository.save(redis);
            clearVoucherCache();
            return voucherRepository.save(entityVoucher);

        } else {
            throw new NoSuchElementException("Không tìm thấy voucher với ID: " + id);
        }
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher addVoucher(Voucher voucher) {
        validateVoucherInput(voucher);

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

        // Lưu vào MySQL như cũ
        Voucher saved = voucherRepository.save(entityVoucher);

        // Thêm bước lưu vào Redis
        VoucherRedis redisVoucher = new VoucherRedis(
                saved.getVoucherID(), saved.getName(), saved.getDescription(),
                saved.getStartDate(), saved.getEndDate(), saved.getDiscountType(),
                saved.getDiscountValue(), saved.getStatus(), saved.getMinValueOrder()
        );
        voucherRedisRepository.save(redisVoucher);
        clearVoucherCache();
        return saved;
    }



    @Override
    public void deleteVoucher(int voucherID) {
        voucherRepository.deleteById(voucherID);
        voucherRedisRepository.deleteById(voucherID);
        clearVoucherCache();
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

    private void clearVoucherCache() {
        Set<String> keys = redisTemplate.keys("voucher:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }




}

