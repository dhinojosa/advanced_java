package com.xyzcorp.exercises.optionals;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    void testGettingGreeceCurrency() {
        Optional.ofNullable(europeanCountriesCapitals
            .get("Greece"))
                .ifPresentOrElse(s -> assertEquals("Athens", s),
                    () -> System.out.println("No currency found"));
    }

    @Test
    void testGettingHungaryCurrency() {
        Optional.ofNullable(europeanCountriesCapitals
            .get("Hungary"))
                .ifPresentOrElse(s -> fail("Should not be found"),
                    () -> assertTrue(true));
    }

    @Test
    void testGettingFromNorwayTheCapitalAndCurrency() {
        Optional<Pair<String, Integer>> capitalAndPopulation =
            getPopulationOfCapitalCity("Norway");

        Pair<String, Integer> expected = new Pair<>("Oslo", 634_293);

        capitalAndPopulation
            .ifPresentOrElse(p -> assertEquals(expected, p),
                () -> fail("Unable to locate"));
    }

    @Test
    void testGettingFromGreeceTheCapitalAndPopulation() {
        Optional<Pair<String, Integer>> capitalAndPopulation =
            getPopulationOfCapitalCity("Greece");

        capitalAndPopulation
            .ifPresentOrElse(p -> fail("Should not be found"),
                () -> assertTrue(true));
    }

    @Test
    void testGettingFromNorwayTheCountryAndCapitalAndCurrency() {
        Optional<Triple<String, String, Integer>> capitalAndPopulation =
            getPopulationOfCountryCapitalCity("Norway");

        Triple<String, String, Integer> expected = new Triple<>("Norway", "Oslo", 634_293);

        capitalAndPopulation
            .ifPresentOrElse(p -> assertEquals(expected, p),
                () -> fail("Unable to locate"));
    }

    private Optional<Pair<String, Integer>> getPopulationOfCapitalCity(String country) {
        return Optional.ofNullable(europeanCountriesCapitals.get(country))
                       .flatMap(cap ->
                           Optional.ofNullable(europeanCapitalPopulation.get(cap))
                                               .map(pop -> new Pair<>(cap, pop)));
    }

    private Optional<Triple<String, String, Integer>> getPopulationOfCountryCapitalCity(String country) {
        return Optional.ofNullable(europeanCountriesCapitals.get(country))
                       .flatMap(cap ->
                           Optional.ofNullable(europeanCapitalPopulation.get(cap))
                                   .map(pop -> new Triple<>(country, cap, pop)));
    }
}
