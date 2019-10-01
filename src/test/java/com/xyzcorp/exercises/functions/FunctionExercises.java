package com.xyzcorp.exercises.functions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionExercises {

    private List<Employee> jkRowlingsEmployees =
        List.of(
            new Employee("Harry", "Potter", 30000),
            new Employee("Hermione", "Granger", 32000),
            new Employee("Ron", "Weasley", 32000),
            new Employee("Albus", "Dumbledore", 40000));
    private Manager jkRowling =
        new Manager("J.K", "Rowling", 46000, jkRowlingsEmployees);

    private List<Employee> georgeLucasEmployees =
        List.of(
            new Employee("Luke", "Skywalker", 33000),
            new Employee("Princess", "Leia", 36000),
            new Employee("Han", "Solo", 36000),
            new Employee("Lando", "Calrissian", 41000));
    private Manager georgeLucas =
        new Manager("J.K", "Rowling", 46000, georgeLucasEmployees);

    private List<Manager> managers = List.of(jkRowling, georgeLucas);

    /**
     * This is a standard JUnit 5 Test with
     * a standard JUnit5 assertion, you can
     * use this as a guide until we can
     * dig deeper into JUnit 5
     */
    @Test
    void testSampleWithStandardJUnit() {
        var result = Math.max(40, 3);
        assertEquals(40, result);
    }

    /**
     * This is a standard JUnit 5 Test with
     * an AssertJ assertion, you can
     * use this as a guide until we can
     * dig deeper into AssertJ
     */
    @Test
    void testSampleWithStandardJUnitAndAssertJAssertion() {
        var result = Math.max(40, 3);
        assertThat(result).isEqualTo(40);
    }
}
