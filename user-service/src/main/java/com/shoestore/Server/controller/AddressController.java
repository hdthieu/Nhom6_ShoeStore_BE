package com.shoestore.Server.controller;

import com.shoestore.Server.dto.AddressDTO;
import com.shoestore.Server.entities.Address;
import com.shoestore.Server.entities.User;
import com.shoestore.Server.repositories.UserRepository;
import com.shoestore.Server.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public List<Address> getAddressByUser(@PathVariable Long userId) {
        return addressService.getAddressByUserId(userId);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable int id) {
        Address address = addressService.getAddressById(id); // Gọi đúng hàm
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Address> addAddress(@PathVariable int userId, @RequestBody AddressDTO addressDTO) {
        // Lấy user từ userId
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User user = optionalUser.get();

        // Chuyển DTO thành entity
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setWard(addressDTO.getWard());
        address.setDistrict(addressDTO.getDistrict());
        address.setUser(user); // gán user cho address

        // Lưu vào DB
        Address savedAddress = addressService.saveAddress(address);
        return ResponseEntity.ok(savedAddress);
    }

}
