package com.shoestore.Server.dto;
//Hung them
public class CategoryDTO {
    private int categoryID;
    private String name;
    private long productCount;

    public CategoryDTO(int categoryID, String name, long productCount) {
        this.categoryID = categoryID;
        this.name = name;
        this.productCount = productCount;
    }


}
