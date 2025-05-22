package com.shoestore.Server.service;
import com.shoestore.Server.entities.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressByUserId(Long userId);
    Address getAddressById(int id);

}
