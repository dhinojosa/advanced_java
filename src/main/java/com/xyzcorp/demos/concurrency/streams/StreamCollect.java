package com.xyzcorp.demos.concurrency.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCollect {
    public static void main(String[] args) {
        Stream<Integer> boxed = IntStream
            .range(0, 100)
            .boxed();
//            .parallel();

        List<Integer> result = boxed.collect(() -> {
            System.out.printf("Creating list on T: %s\n", Thread.currentThread().getName());
            return new ArrayList<>();
        }, (integers, integer) -> {
            System.out.printf("Adding %d to the list on T: %s\n", integer, Thread.currentThread().getName());
            integers.add(integer);
        }, (integers, integers2) -> {
            System.out.printf("Combining %s and %s on T: %s\n", integers, integers2, Thread.currentThread().getName());
            integers.addAll(integers2);
        });

        System.out.println(result);
    }
}
