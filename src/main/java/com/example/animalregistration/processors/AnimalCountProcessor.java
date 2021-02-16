package com.example.animalregistration.processors;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AnimalCountProcessor {
    public List<String> processAnimals(List<String[]> animals, List<Map<String, Predicate<String[]>>> rules) {
        return rules.parallelStream().map(
                rulesGroup -> {
                    long count = animals.parallelStream().filter(
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
