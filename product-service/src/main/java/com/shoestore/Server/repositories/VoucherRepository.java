package com.shoestore.Server.repositories;

import com.shoestore.Server.entities.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    List<Voucher> findByNameContainingIgnoreCase(String name);
    List<Voucher> findByStatusAndNameContainingIgnoreCase(String status, String name);
    List<Voucher> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate start, LocalDate end);
    List<Voucher> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatusAndNameContainingIgnoreCase(
            LocalDate startDate, LocalDate endDate, String status, String search);
    List<Voucher> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatus(LocalDate startDate, LocalDate endDate, String status);
    // Tìm voucher theo trạng thái
    List<Voucher> findByStatus(String status);
    // Tìm tất cả voucher
    List<Voucher> findAll();

}

