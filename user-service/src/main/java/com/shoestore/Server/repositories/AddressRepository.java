package com.shoestore.Server.repositories;


import com.shoestore.Server.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserUserID(Long userId);
}
