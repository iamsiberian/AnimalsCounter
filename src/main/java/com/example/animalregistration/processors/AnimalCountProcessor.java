package com.example.animalregistration.processors;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AnimalCountProcessor {
    private static final String DELIMITER = ",";

    public List<String> processAnimals(Supplier<Stream<String>> animals, List<Map<String, Predicate<String[]>>> rules) {
        return rules.parallelStream().map(
                rulesGroup -> {
                    long count = animals.get().parallel()
                            .map(animal -> animal.split(DELIMITER))
                            .filter(
                                    rulesGroup.values().stream().reduce(Predicate::and).orElse(t -> false)
                            ).count();

                    return String.format("""
                    Counted animals: %d
                    For rules: %s
                    
                    """, count, rulesGroup.keySet().toString());
                }
        ).collect(Collectors.toList());
    }
}
