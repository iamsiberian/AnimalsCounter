package com.example.animalregistration.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    HERBIVOROUS("ТРАВОЯДНОЕ"),
    CARNIVOROUS("ПЛОТОЯДНОЕ"),
    OMNIVOROUS("ВСЕЯДНОЕ");

    private final String stringType;

    public static Type fromStringValue(String value) {

        for (Type type : Type.values()) {
            if (type.getStringType().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Type.class.getCanonicalName() + "." + value);
    }
}
