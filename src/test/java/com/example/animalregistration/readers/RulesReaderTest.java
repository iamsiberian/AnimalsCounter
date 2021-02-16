package com.example.animalregistration.readers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RulesReaderTest {
    private static final String OR = " OR ";
    private static final String NEGATE = "! ";
    private static final String SOMETHING_RULE = "trash";
    private static final String DELIMITER = ",";
    private static final String HERBIVOROUS_RULE = "ТРАВОЯДНОЕ";
    private static final String OMNIVOROUS_RULE = "ВСЕЯДНОЕ";
    private static final String CARNIVOROUS_RULE = "ПЛОТОЯДНОЕ";

    private RulesReader rulesReader;
    private BufferedReader bufferedReader;
    public static final String SMALL_RULE = "МАЛЕНЬКОЕ";

    @BeforeEach
    void setUp() {
        bufferedReader = mock(BufferedReader.class);
        rulesReader = new RulesReader(bufferedReader);
    }

    @Test
    void testCreateMultipleRule() throws IOException {
        final String MultipleRule = HERBIVOROUS_RULE + OR + CARNIVOROUS_RULE;
        when(bufferedReader.ready()).thenReturn(true).thenReturn(false);
        when(bufferedReader.readLine()).thenReturn(MultipleRule);

        List<Map<String, Predicate<String[]>>> actualRuleGroup = rulesReader.readRules();

        assertEquals(1, actualRuleGroup.size());
        assertTrue(actualRuleGroup.get(0).containsKey(MultipleRule));
        Predicate<String[]> actualPredicate = actualRuleGroup.get(0).get(MultipleRule);
        assertTrue(actualPredicate.test(new String[]{ HERBIVOROUS_RULE, SOMETHING_RULE }));
        assertTrue(actualPredicate.test(new String[]{ CARNIVOROUS_RULE, SOMETHING_RULE }));
        assertFalse(actualPredicate.test(new String[]{ SOMETHING_RULE, SOMETHING_RULE }));
    }

    @Test
    void testCreateSingleRule() throws IOException {
        when(bufferedReader.ready()).thenReturn(true).thenReturn(false);
        when(bufferedReader.readLine()).thenReturn(OMNIVOROUS_RULE);

        List<Map<String, Predicate<String[]>>> actualRuleGroup = rulesReader.readRules();

        assertEquals(1, actualRuleGroup.size());
        assertTrue(actualRuleGroup.get(0).containsKey(OMNIVOROUS_RULE));
        Predicate<String[]> actualPredicate = actualRuleGroup.get(0).get(OMNIVOROUS_RULE);
        assertTrue(actualPredicate.test(new String[]{OMNIVOROUS_RULE, SOMETHING_RULE }));
        assertFalse(actualPredicate.test(new String[]{ SOMETHING_RULE, SOMETHING_RULE }));
    }

    @Test
    void testCreateNegateRule() throws IOException {
        final String OmnivorousNegateRule = NEGATE + OMNIVOROUS_RULE;
        when(bufferedReader.ready()).thenReturn(true).thenReturn(false);
        when(bufferedReader.readLine()).thenReturn(OmnivorousNegateRule);

        List<Map<String, Predicate<String[]>>> actualRuleGroup = rulesReader.readRules();

        assertEquals(1, actualRuleGroup.size());
        assertTrue(actualRuleGroup.get(0).containsKey(OmnivorousNegateRule));
        Predicate<String[]> actualPredicate = actualRuleGroup.get(0).get(OmnivorousNegateRule);

        assertFalse(actualPredicate.test(new String[]{ OMNIVOROUS_RULE, SOMETHING_RULE }));
        assertTrue(actualPredicate.test(new String[]{ SOMETHING_RULE, SOMETHING_RULE }));
    }

    @Test
    void testCreateRuleGroupWithMultipleNegateRule() throws IOException {
        final String OmnivorousNegateRule = NEGATE + OMNIVOROUS_RULE;
        final String MultipleRule = OmnivorousNegateRule + OR + CARNIVOROUS_RULE;
        final String MultipleRuleGroup = MultipleRule + DELIMITER + SMALL_RULE;

        when(bufferedReader.ready()).thenReturn(true).thenReturn(false);
        when(bufferedReader.readLine()).thenReturn(MultipleRuleGroup);

        List<Map<String, Predicate<String[]>>> actualRuleGroup = rulesReader.readRules();

        assertEquals(1, actualRuleGroup.size());
        assertTrue(actualRuleGroup.get(0).containsKey(MultipleRule));
        Predicate<String[]> actualFirstPredicate = actualRuleGroup.get(0).get(MultipleRule);
        assertFalse(actualFirstPredicate.test(new String[]{ OMNIVOROUS_RULE, SOMETHING_RULE }));
        assertTrue(actualFirstPredicate.test(new String[]{ CARNIVOROUS_RULE, SOMETHING_RULE }));
        assertTrue(actualFirstPredicate.test(new String[]{ SOMETHING_RULE, SOMETHING_RULE }));
        assertTrue(actualRuleGroup.get(0).containsKey(SMALL_RULE));
        Predicate<String[]> secondPredicate = actualRuleGroup.get(0).get(SMALL_RULE);
        assertTrue(secondPredicate.test(new String[]{SMALL_RULE, SOMETHING_RULE }));
        assertFalse(secondPredicate.test(new String[]{ SOMETHING_RULE, SOMETHING_RULE }));
    }
}
