package com.shoestore.Server.dto;



import java.util.List;

public class BrandResponseDTO {
    private List<BrandDTO> brandDTOs;

    public BrandResponseDTO() {}

    public BrandResponseDTO(List<BrandDTO> brandDTOs) {
        this.brandDTOs = brandDTOs;
    }

    public List<BrandDTO> getBrandDTOs() {
        return brandDTOs;
    }

    public void setBrandDTOs(List<BrandDTO> brandDTOs) {
        this.brandDTOs = brandDTOs;
    }
}
