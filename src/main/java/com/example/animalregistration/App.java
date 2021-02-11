package com.example.animalregistration;

import com.example.animalregistration.processors.AnimalCountProcessor;
import com.example.animalregistration.readers.AnimalsReader;
import com.example.animalregistration.readers.RulesReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class App {
    private static final int ARG_LENGTH = 2;

    public static void main(String[] args) throws AppException {
        List<String[]> animals;
        List<Map<String, Predicate<String[]>>> rules;

        if (args.length == ARG_LENGTH) {
            try (
                    BufferedReader animalsReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
                    BufferedReader rulesReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[1]), StandardCharsets.UTF_8))
            ) {
                AnimalsReader animalsBufferedReader = new AnimalsReader(animalsReader);
                animals = animalsBufferedReader.getAnimals();

                RulesReader rulesBufferedReader = new RulesReader(rulesReader);
                rules = rulesBufferedReader.readRules();
            } catch (IOException e) {
                throw new AppException("Error while reading files: ", e);
            }
            AnimalCountProcessor.processAnimals(animals, rules);
        } else {
            System.err.println("Usage: java -jar *.jar animals.file rules.file");
            System.exit(1);
        }
    }
}
