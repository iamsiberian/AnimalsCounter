package com.example.animalregistration.readers;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AnimalsReader {
    private static final String DELIMITER = ",";

    private final BufferedReader animalsBufferedReader;

    public List<String[]> getAnimals() throws IOException {
        List<String[]> animals = new ArrayList<>();

        while (animalsBufferedReader.ready()) {
            animals.add(animalsBufferedReader.readLine().split(DELIMITER));
        }

        return animals;
    }
}
