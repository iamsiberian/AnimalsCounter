package com.example.animalregistration.readers;

import com.example.animalregistration.domain.Animal;
import com.example.animalregistration.domain.enums.Height;
import com.example.animalregistration.domain.enums.Type;
import com.example.animalregistration.domain.enums.Weight;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AnimalsReader {
    private static final String DELIMITER = ",";

    private final BufferedReader animalsBufferedReader;

    public List<Animal> getAnimals() throws IOException {
        List<Animal> animals = new ArrayList<>();

        while (animalsBufferedReader.ready()) {
            String line = animalsBufferedReader.readLine();
            String[] animalFields = line.split(DELIMITER);

            try {
                Weight weight = Weight.fromStringValue(animalFields[0]);
                Height height = Height.fromStringValue(animalFields[1]);
                Type type = Type.fromStringValue(animalFields[2]);

                animals.add(new Animal(weight, height, type));
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.printf("warning: error while creating animal from %s%n", Arrays.toString(animalFields));
            }
        }

        return animals;
    }
}
