package com.shoestore.Server.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Size {
    SIZE_36(36),
    SIZE_37(37),
    SIZE_38(38),
    SIZE_39(39),
    SIZE_40(40),
    SIZE_41(41),
    SIZE_42(42),
    SIZE_43(43),
    SIZE_44(44);

    private final int sizeValue;

    // Constructor bắt buộc phải là private (hoặc mặc định)
    Size(int sizeValue) {
        this.sizeValue = sizeValue;
    }

    @Override
    public String toString() {
        return String.valueOf(sizeValue);
    }
}


