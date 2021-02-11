package com.example.animalregistration.domain;

import com.example.animalregistration.domain.enums.Height;
import com.example.animalregistration.domain.enums.Type;
import com.example.animalregistration.domain.enums.Weight;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Animal {
    private Weight weight;
    private Height height;
    private Type type;
}
