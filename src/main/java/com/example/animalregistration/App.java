package com.example.animalregistration;

import com.example.animalregistration.processors.AnimalCountProcessor;
import com.example.animalregistration.readers.RulesReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class App {
    private static final int ARG_LENGTH = 2;
    private static final AnimalCountProcessor animalCountProcessor = new AnimalCountProcessor();

    public static void main(String[] args) throws AppException {
        List<Map<String, Predicate<String[]>>> rules;

        if (args.length == ARG_LENGTH) {
            final String pathToAnimalFile = args[0];
            final String pathToRulesFile = args[1];

            try (
                    FileInputStream rulesFileInputStream = new FileInputStream(pathToRulesFile);
                    InputStreamReader rulesInputStreamReader = new InputStreamReader(rulesFileInputStream, StandardCharsets.UTF_8);
                    BufferedReader rulesReader = new BufferedReader(rulesInputStreamReader)
            ) {
                RulesReader rulesBufferedReader = new RulesReader(rulesReader);
                rules = rulesBufferedReader.readRules();
            } catch (IOException e) {
                throw new AppException("Error while reading files: ", e);
            }

            Supplier<Stream<String>> animalsStreamSupplier = () -> {
                try {
                    return Files.lines(Path.of(pathToAnimalFile), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new AppException("Error while obtain animal stream: ", e);
                }
            };
            List<String> answers = animalCountProcessor.processAnimals(animalsStreamSupplier, rules);
            System.out.println(answers);

        } else {
            System.err.println("Usage: java -jar *.jar animals.file rules.file");
            System.exit(1);
        }
    }
}
