package com.shoestore.Server.controller;

import com.shoestore.Server.dto.BrandDTO;
import com.shoestore.Server.dto.BrandResponseDTO;
import com.shoestore.Server.entities.Brand;
import com.shoestore.Server.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<BrandResponseDTO> getAllBrands() {
        List<Brand> brands = brandService.getAllBrand(); // lấy từ DB

        List<BrandDTO> brandDTOs = brands.stream()
                .map(b -> new BrandDTO((long) b.getBrandID(), b.getName())) // nếu BrandDTO có constructor (id, name)
                .collect(Collectors.toList());

        BrandResponseDTO response = new BrandResponseDTO(brandDTOs);
        return ResponseEntity.ok(response);
    }

}

