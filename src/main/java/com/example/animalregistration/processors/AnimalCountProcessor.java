package com.example.animalregistration.processors;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class AnimalCountProcessor {
    public static void processAnimals(List<String[]> animals, List<Map<String, Predicate<String[]>>> rules) {
        rules.parallelStream().forEach(
                rulesMap -> {
                    long count = animals.parallelStream().filter(
                            rulesMap.values().stream().reduce(Predicate::and).orElse(t -> false)
                    ).count();

                    System.out.printf("""
                    Counted animals: %d
                    For rules: %s     
                    %n""", count, rulesMap.keySet().toString());
                }
        );
    }
}
