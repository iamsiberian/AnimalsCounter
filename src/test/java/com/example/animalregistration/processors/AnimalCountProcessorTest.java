package com.example.animalregistration.processors;

import com.example.animalregistration.readers.RulesReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnimalCountProcessorTest {
    private final static String ANSWER_1 = """
                    Counted animals: 24
                    For rules: [ВСЕЯДНОЕ, ! ВЫСОКОЕ]
                    
                    """;
    private final static String ANSWER_2 = """
                    Counted animals: 24
                    For rules: [ТРАВОЯДНОЕ OR ПЛОТОЯДНОЕ, МАЛЕНЬКОЕ]
                    
                    """;
    private final static String ANSWER_3 = """
                    Counted animals: 36
                    For rules: [ТРАВОЯДНОЕ]
                    
                    """;
    private final static String DELIMITER = ",";

    private final AnimalCountProcessor animalCountProcessor = new AnimalCountProcessor();
    private final RulesReader rulesReader = new RulesReader(null);

    private List<String[]> animals;
    private List<Map<String, Predicate<String[]>>> rules;

    @BeforeEach
    void setUp() {
        animals = List.of(
                new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ЛЕГКОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "СРЕДНЕЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ТРАВОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ПЛОТОЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" },
                new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" }, new String[]{ "ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ" }
        );
        
        rules = List.of(
                rulesReader.createRuleGroup("ТРАВОЯДНОЕ".split(DELIMITER)),
                rulesReader.createRuleGroup("ТРАВОЯДНОЕ OR ПЛОТОЯДНОЕ,МАЛЕНЬКОЕ".split(DELIMITER)),
                rulesReader.createRuleGroup("ВСЕЯДНОЕ,! ВЫСОКОЕ".split(DELIMITER))
        );
    }
    
    @Test
    void testProcessAnimals() {
        List<String> answers = animalCountProcessor.processAnimals(animals, rules);
        assertEquals(3, answers.size());
        assertTrue(answers.contains(ANSWER_1));
        assertTrue(answers.contains(ANSWER_2));
        assertTrue(answers.contains(ANSWER_3));
    }
}
