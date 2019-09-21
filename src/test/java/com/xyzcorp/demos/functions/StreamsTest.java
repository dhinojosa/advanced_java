package com.xyzcorp.demos.functions;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static org.junit.Assert.assertEquals;

public class StreamsTest {

    @Test
    public void testBasicStream() {
        List<Integer> integers = Arrays.asList(1, 4, 5);
        List<Integer> result = integers.stream()
                                       .map(x -> x + 1).collect(Collectors.toList());
        assertEquals(result, Arrays.asList(2, 5, 6));
    }

    @Test
    public void testBasicWithOurOwnCollection() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> result = numbers.stream().parallel().map(x -> {
            System.out.format("%s: map: %s\n", Thread.currentThread().getName(), x);
            return x + 1;
        }).collect(
                ArrayList::new,
                (integers, integer) -> {
                    System.out.format("%s: adding integer: %s\n", Thread.currentThread().getName(), integer);
                    integers.add(integer);
                }, (left, right) -> {
                    synchronized (numbers) {
                        System.out.format("%s: left = %s\n", Thread.currentThread().getName(), left);
                        System.out.format("%s: right = %s\n", Thread.currentThread().getName(), right);
                        left.addAll(right);
                        System.out.format("%s: combined = %s\n", Thread.currentThread().getName(), left);
                    }
                });
        assertEquals(result, Arrays.asList(2, 5, 6));
    }

    @Test
    public void testSum() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer sum = numbers.stream().map(x -> x + 1).mapToInt(value -> value).sum();
        Integer sum2 = numbers.stream().map(x -> x + 1).collect(Collectors.summingInt(x -> x));
    }

    @Test
    public void testQuestionOnTypes() {
        String collect = Arrays.asList(1, 2, 3, 4)
                               .stream()
                               .map(x -> x + 1)
                               .map(Object::toString)
                               .collect(Collectors.joining(", "));
        assertEquals("2, 3, 4, 5", collect);
    }

    @Test
    public void testConvertToStream() {
        IntStream range = IntStream.range(5, 10);
        IntStream intStream = range.filter(x -> x % 2 == 0);
        Stream<Integer> boxed = intStream.boxed();
        Set<Integer> set = boxed.collect(Collectors.toSet());
        System.out.println(set);
    }

    @Test
    public void testIntStreamSummaryStatistics() {
        Stream<Integer> numbers = Stream.of(100, 33, 22, 400, 30);
        IntStream intStream = numbers.mapToInt(x -> x);
        System.out.println(intStream.summaryStatistics());
    }

    @Test
    public void testStreamWithPeek() {
        List<Integer> result = Stream.of(1, 2, 3, 4, 5)
                                     .map(x -> x + 1)
                                     .peek(System.out::println)
                                     .filter(x -> x % 2 == 0)
                                     .collect(Collectors.toList());
        System.out.println(result);
    }

    @Test
    public void testLimit() {
        Stream<Integer> integerStream = Stream.iterate(0, x -> x + 1); //Goes on forever!
        List<Integer> result = integerStream.map(x -> x + 4)
                                            .parallel()
                                            .peek(x -> System.out.format("%s: element %s\n", Thread.currentThread().getName(), x))
                                            .limit(10)
                                            .collect(Collectors.toList());
        System.out.println(result);
    }

    @Test
    public void testFlatMap() {
        Stream<Stream<Integer>> streamStream =
                Stream.of(1, 2, 3, 4).map(x -> Stream.of(-x, x, x + 2));

        Stream<Integer> stream =
                Stream.of(1, 2, 3, 4).flatMap(x -> Stream.of(-x, x, x + 2));

        Stream<LocalDateTime> stream1 = Stream.generate(LocalDateTime::now)
                                              .flatMap(x -> Stream.of(x, x.plusYears(10)))
                                              .limit(20);

        System.out.println("stream1 = " + stream1);


        Set<LocalDateTime> dateTimes = Stream.generate(LocalDateTime::now)
                                             .flatMap(x -> Stream.generate(() -> x.plusMinutes(10)).limit(10))
                                             .limit(10)
                                             .collect(Collectors.toSet());

        System.out.println("dateTimes = " + dateTimes);

        Stream<Integer> s1 = Stream.of(10 + 20);

        Stream<Integer> s2 = Stream.of(50 + 19);

        Stream<Integer> s3 = Stream.of(0);

        List<Integer> result =
                s1.flatMap(x ->
                        s2.flatMap(y ->
                                s3.flatMap(z -> {
                                    if (z == 0) return Stream.empty();
                                    else return Stream.of(x + y / z);
                                }))).collect(Collectors.toList());

        System.out.println("result = " + result);

    }


    public int factorial(int value) {
        IntStream range = IntStream.range(1, value + 1);
        return range.reduce(1, (left, right) -> {
            System.out.format("Left: %d, Right: %d\n", left, right);
            return left * right;
        });
    }

    @Test
    public void testTheMysteryMathProperty() {
        System.out.println(factorial(5));
    }

    @Test
    public void testReduceWithASeed() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        Integer reduction = stream.reduce(0, (total, next) -> {
            System.out.format("total: %d, next: %d\n", total, next);
            return total + next;
        });
        System.out.println(reduction);
    }

    @Test
    public void testSortedWithComparator() {
        Stream<String> stream =
                Stream.of("Apple", "Orange", "Banana", "Tomato", "Grapes", "Kiwi");

        List<String> list = stream
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println(list);
    }

    @Test
    public void testSortedWithComparatorLevels() {
        Stream<String> stream =
                Stream.of("Apple", "Orange", "Banana", "Tomato", "Grapes", "Plum", "Kiwi");
        System.out.println(stream
                .sorted(Comparator
                        .comparing(String::length).thenComparing(identity()))
                .collect(Collectors.toList()));
    }

    @Test
    public void testGrouping() {
        IntStream stream = IntStream.range(0, 150403010);
        Map<Boolean, List<Integer>> groups = stream.parallel().boxed()
                                                   .collect(Collectors.groupingByConcurrent(i -> i % 3 == 0));
        System.out.println(groups);
    }

    @Test
    public void testPartitioning() {
        Stream<String> stream =
                Stream.of("Apple", "Orange", "Banana", "Tomato", "Grapes");
        Map<Boolean, List<String>> partition = stream.collect
                (Collectors.partitioningBy(s -> "AEIOU"
                        .indexOf(s.toUpperCase().charAt(0)) >= 0));
        System.out.println(partition);
    }

    @Test
    public void testJoining() {
        Stream<String> stream =
                Stream.of("Apple", "Orange", "Banana", "Tomato", "Grapes");
        System.out.println(stream.collect(Collectors.joining(", ", "{", "}")));
    }

    @Test
    public void testDiscoveringAmerica() {
        List<String> america = ZoneId.getAvailableZoneIds()
                                     .stream()
                                     .filter(s -> s.startsWith("America"))
                                     .map(s -> s.substring(s.lastIndexOf('/') + 1))
                                     .distinct()
                                     .sorted()
                                     .collect(Collectors.toList());
        System.out.println(america);
    }
}











