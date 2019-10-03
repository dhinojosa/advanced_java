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
        List<String> actual = Arrays.asList(1, 50, 100, 200, 921)
                                    .stream()
                                    .map(Integer::toHexString)
                                    .collect(Collectors.toList());
        assertThat(actual).contains("1", "32", "64", "c8", "399");
    }

    @Test
    void testConvertIntegerListToDouble() {
        List<Double> actual = Arrays.asList(1, 50, 100, 200, 921)
                                    .stream()
                                    .map(Double::valueOf)
                                    .collect(Collectors.toList());
        assertThat(actual).contains(1.0, 50.0, 100.0, 200.0, 921.0);
    }

    @Test
    void testFactorialCreator() {
        Integer result = Factorial.factorial(5);
        assertThat(result).isEqualTo(120);
    }

    @Test
    void testFindTopFiveEmployees() {
        Stream<Employee> allEmployees =
            Stream.concat(jkRowlingsEmployees.stream(),
            georgeLucasEmployees.stream());
        String result = allEmployees
            .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
            .limit(5)
            .map(e -> String.format("%s, %d",
                e.getFirstName() + " " + e.getLastName(),
                e.getSalary()))
            .collect(Collectors.joining("\n"));

        System.out.println(result);
    }

    @Test
    void testAllEmployeesSalary() {
        int actual = managers.stream()
                             .flatMap(man -> man.getEmployees().stream())
                             .map(Employee::getSalary)
                             .mapToInt(i -> i)
                             .sum();
        assertEquals(280000, actual);
    }

    @Test
    void testAllEmployeesSalaryWithManager() {
        int actual = managers.stream()
                             .flatMap(man -> Stream.concat(Stream.of(man),
                                 man.getEmployees().stream()))
                             .map(Employee::getSalary)
                             .mapToInt(i -> i)
                             .sum();
        assertEquals(372000, actual);
    }

    @Test
    void testWhichManagersEmployeesArePaidMore() {
        Map<Manager, Integer> managers = this.managers.stream()
                                                      .collect(Collectors.toMap(m -> m, m -> m.getEmployees().stream().mapToInt(Employee::getSalary).sum()));
        Optional<Map.Entry<Manager, Integer>> max =
            managers.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));

        max.ifPresentOrElse(System.out::println, () -> fail("Didn't work"));
    }

    @Test
    void testMakeAMapOfJKAndGeorge() {
        var streams = managers.stream()
                              .map(m -> m.getEmployees()
                                         .stream()
                                         .collect(Collectors.toMap(Function.identity(), employee -> m)));

        Optional<Map<Employee, Manager>> possibleReduce =
            streams.reduce((map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });

        possibleReduce.ifPresentOrElse(map -> {
            assertThat(map).contains(
                Map.entry(new Employee("Princess", "Leia", 36000), georgeLucas)
            );
        }, () -> fail("No Result"));
    }
}
