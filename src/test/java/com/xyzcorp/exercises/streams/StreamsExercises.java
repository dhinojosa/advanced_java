package com.xyzcorp.exercises.streams;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StreamsExercises {
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
        new Manager("George", "Lucas", 46000, georgeLucasEmployees);

    private List<Manager> managers = List.of(jkRowling, georgeLucas);


    @Test
    void testConvertIntegerListToHexadecimal() {

    }

    @Test
    void testConvertIntegerListToDouble() {

    }

    @Test
    void testFactorialUsingStreams() {

    }

    @Test
    void testFindTopFiveEmployees() {

    }

    @Test
    void testAllEmployeesSalaryWithManager() {

    }

    @Test
    void testWhichManagersEmployeesArePaidMore() {

    }

    @Test
    void testCreateAMapOfEmployeeKeyWithManagerValue() {

    }
}
