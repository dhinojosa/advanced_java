package com.xyzcorp.exercises.optionals;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

class OptionalExercises {

    private Map<String, String> europeanCountriesCapitals = Map.ofEntries(
        new AbstractMap.SimpleEntry<>("France", "Paris"),
        new AbstractMap.SimpleEntry<>("Britain", "London"),
        new AbstractMap.SimpleEntry<>("Bulgaria", "Sofia"),
        new AbstractMap.SimpleEntry<>("Spain", "Madrid"),
        new AbstractMap.SimpleEntry<>("Greece", "Athens"),
        new AbstractMap.SimpleEntry<>("Poland", "Warsaw"),
        new AbstractMap.SimpleEntry<>("Germany", "Berlin"),
        new AbstractMap.SimpleEntry<>("Norway", "Oslo"),
        new AbstractMap.SimpleEntry<>("Finland", "Helsinki"),
        new AbstractMap.SimpleEntry<>("Sweden", "Stockholm"),
        new AbstractMap.SimpleEntry<>("Portugal", "Lisbon"));

    private Map<String, Integer> europeanCapitalPopulation = Map.ofEntries(
        new AbstractMap.SimpleEntry<>("Paris", 2_141_000),
        new AbstractMap.SimpleEntry<>("London", 8_136_000),
        new AbstractMap.SimpleEntry<>("Sofia", 1_236_000),
        new AbstractMap.SimpleEntry<>("Spain", 3_174_000),
        new AbstractMap.SimpleEntry<>("Warsaw", 1_765_000),
        new AbstractMap.SimpleEntry<>("Berlin", 3_575_000),
        new AbstractMap.SimpleEntry<>("Oslo", 634_293),
        new AbstractMap.SimpleEntry<>("Helsinki", 631_695),
        new AbstractMap.SimpleEntry<>("Stockholm", 965_232),
        new AbstractMap.SimpleEntry<>("Lisbon", 504_718));

    @Test
    void testGettingGreeceCapital() {

    }

    @Test
    void testGettingHungaryCapital() {

    }

    @Test
    void testGettingFromNorwayTheCapitalAndPopulation() {

    }

    @Test
    void testGettingFromGreeceTheCapitalAndPopulation() {

    }

    @Test
    void testGettingFromNorwayTheCountryAndCapital() {

    }
}
