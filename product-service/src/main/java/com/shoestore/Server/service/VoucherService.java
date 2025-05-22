package com.shoestore.Server.service;

import com.shoestore.Server.entities.Voucher;

import java.time.LocalDate;
import java.util.List;

public interface VoucherService{
    public List<Voucher> getAllVouchers();

    public Voucher addVoucher(Voucher voucher);
    public void deleteVoucher(int voucherID);
    public Voucher updateVoucher(int id, Voucher voucher);
//    public List<Voucher> findVoucherByCodeOrDate(LocalDate startDate, LocalDate endDate);
public List<Voucher> searchVouchersWithFilters(LocalDate startDate, LocalDate endDate, String status);
}
