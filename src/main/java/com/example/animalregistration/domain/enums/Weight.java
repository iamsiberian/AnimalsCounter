package com.example.animalregistration.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weight {
    LIGHT("ЛЕГКОЕ"),
    MIDDLE("СРЕДНЕЕ"),
    HEAVY("ТЯЖЕЛОЕ");

    private final String stringWeight;

    public static Weight fromStringValue(String value) {

        for (Weight weight : Weight.values()) {
            if (weight.getStringWeight().equalsIgnoreCase(value)) {
                return weight;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Weight.class.getCanonicalName() + "." + value);
    }
}
