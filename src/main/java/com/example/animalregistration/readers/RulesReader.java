package com.example.animalregistration.readers;

import com.example.animalregistration.domain.Animal;
import com.example.animalregistration.domain.enums.Height;
import com.example.animalregistration.domain.enums.Type;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@AllArgsConstructor
public class RulesReader {
    private static final String DELIMITER = ",";
    private static final String OR = " OR ";
    private static final String NOT = "! ";
    private static final Map<String, Predicate<Animal>> rulesRepository = new HashMap<>();

    private final BufferedReader rulesBufferedReader;

    static {
        rulesRepository.put(
                Type.HERBIVOROUS.getStringType(),
                animal -> animal.getType() == Type.HERBIVOROUS
        );
        rulesRepository.put(
                Type.HERBIVOROUS.getStringType() + OR + Type.CARNIVOROUS.getStringType(),
                animal -> animal.getType() == Type.HERBIVOROUS || animal.getType() == Type.CARNIVOROUS
        );
        rulesRepository.put(
                Height.SMALL.getStringHeight(),
                animal -> animal.getHeight() == Height.SMALL
        );
        rulesRepository.put(
                Type.OMNIVOROUS.getStringType(),
                animal -> animal.getType() == Type.OMNIVOROUS
        );
        rulesRepository.put(
                NOT + Height.HIGH.getStringHeight(),
                animal -> animal.getHeight() != Height.HIGH
        );
    }

    public List<Map<String, Predicate<Animal>>> readRules() throws IOException {
        List<Map<String, Predicate<Animal>>> rules = new ArrayList<>();
        while (rulesBufferedReader.ready()) {
            Map<String, Predicate<Animal>> rulesMap = new HashMap<>();
            for (String rule : rulesBufferedReader.readLine().split(DELIMITER)) {
                if (rulesRepository.containsKey(rule)) {
                    rulesMap.put(rule, rulesRepository.get(rule));
                }
            }
            rules.add(rulesMap);
        }
        return rules;
    }
}
