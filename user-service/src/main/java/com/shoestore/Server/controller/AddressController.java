package com.shoestore.Server.controller;

import com.shoestore.Server.dto.AddressDTO;
import com.shoestore.Server.entities.Address;
import com.shoestore.Server.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    public List<Address> getAddressByUser(@PathVariable Long userId) {
        return addressService.getAddressByUserId(userId);
    }
}
