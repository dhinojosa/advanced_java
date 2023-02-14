package com.xyzcorp.streams;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelCollectionStreamTest {
    @Test
    void testCollectionOfParallelStream() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> result = numbers.stream().parallel().map(x -> {
            System.out.format("%s: map: %s\n",
                Thread.currentThread().getName(), x);
            return x + 1;
        }).collect(
            ArrayList::new,
            (integers, integer) -> {
                System.out.format("%s: adding integer: %s\n",
                    Thread.currentThread().getName(), integer);
                integers.add(integer);
            }, (left, right) -> {
                synchronized (numbers) {
                    System.out.format("%s: left = %s\n",
                        Thread.currentThread().getName(), left);
                    System.out.format("%s: right = %s\n",
                        Thread.currentThread().getName(), right);
                    left.addAll(right);
                    System.out.format("%s: combined = %s\n",
                        Thread.currentThread().getName(), left);
                }
            });
        assertThat(result).isEqualTo(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,
            10));
    }

    @Test
    void testCollectionOfParallelStreamCleanedUp() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> result = numbers
            .stream().parallel()
            .map(x -> x + 1).collect(Collectors.toList());
        assertThat(result).isEqualTo(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,
            10));
    }
}
