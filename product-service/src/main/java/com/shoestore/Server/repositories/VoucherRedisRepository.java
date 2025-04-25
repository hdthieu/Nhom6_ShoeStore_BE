package com.shoestore.Server.repositories;

import com.shoestore.Server.entities.Voucher;
import com.shoestore.Server.entities.VoucherRedis;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface VoucherRedisRepository extends CrudRepository<VoucherRedis, Integer> {
    
}
