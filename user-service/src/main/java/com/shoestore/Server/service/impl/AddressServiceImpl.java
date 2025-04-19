package com.shoestore.Server.service.impl;




import com.shoestore.Server.entities.Address;
import com.shoestore.Server.repositories.AddressRepository;
import com.shoestore.Server.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAddressByUserId(Long userId) {
        return addressRepository.findByUserUserID(userId);
    }
    @Override
    public Address getAddressById(int id) {
        return addressRepository.findById((long) id).orElse(null);
    }

}
