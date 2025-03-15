package com.shoestore.Server.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Color {
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    BLACK("Black"),
    WHITE("White"),
    PINK("Pink");

    private final String colorName;

    // Constructor cần khai báo rõ ràng
    Color(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }
}
