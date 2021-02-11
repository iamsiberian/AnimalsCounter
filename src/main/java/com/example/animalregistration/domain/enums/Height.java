package com.example.animalregistration.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Height {
    SMALL("МАЛЕНЬКОЕ"),
    SHORT("НЕВЫСОКОЕ"),
    HIGH("ВЫСОКОЕ");

    private final String stringHeight;

    public static Height fromStringValue(String value) {

        for (Height height : Height.values()) {
            if (height.getStringHeight().equalsIgnoreCase(value)) {
                return height;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Height.class.getCanonicalName() + "." + value);
    }
}
