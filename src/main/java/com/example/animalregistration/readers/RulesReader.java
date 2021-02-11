package com.example.animalregistration.readers;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@AllArgsConstructor
public class RulesReader {
    private static final String DELIMITER = ",";
    private static final String EMPTY = "";
    private static final String OR = " OR ";
    private static final String NOT = "! ";

    private final BufferedReader rulesBufferedReader;

    public List<Map<String, Predicate<String[]>>> readRules() throws IOException {
        List<Map<String, Predicate<String[]>>> rules = new ArrayList<>();
        while (rulesBufferedReader.ready()) {
            Map<String, Predicate<String[]>> rulesMap = new HashMap<>();
            for (String rule : rulesBufferedReader.readLine().split(DELIMITER)) {
                rulesMap.put(rule, createRule(rule));
            }
            rules.add(rulesMap);
        }
        return rules;
    }

    private Predicate<String[]> createRule(String ruleGroup) {
        return isMultipleRule(ruleGroup)
                ? createMultipleRule(ruleGroup)
                : createSingleRule(ruleGroup);
    }

    private Predicate<String[]> createMultipleRule(String ruleGroup) {
        String[] multipleRules = ruleGroup.split(OR);
        return Arrays.stream(multipleRules).map(this::createSingleRule).reduce(Predicate::or).orElse(t -> false);
    }

    private Predicate<String[]> createSingleRule(String rule) {
        return isContainsInverting(rule)
                ? createNotPredicate(rule)
                : createPredicate(rule);
    }

    private Predicate<String[]> createPredicate(String rule) {
        return (String[] animalFields) -> {
            for (String internalField : animalFields) {
                if (internalField.equals(rule)) {
                    return true;
                }
            }
            return false;
        };
    }

    private Predicate<String[]> createNotPredicate(String rule) {
        return createPredicate(rule.replace(NOT, EMPTY)).negate();
    }

    private boolean isMultipleRule(String ruleGroup) {
        return ruleGroup.contains(OR);
    }

    private boolean isContainsInverting(String rule) {
        return rule.contains(NOT);
    }
}
