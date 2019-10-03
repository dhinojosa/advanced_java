package com.xyzcorp.demos.functions;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.function.Function.identity;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class StreamsTest {

    @Test
    public void testBasicStreamFromList() {
        List<Integer> integers = Arrays.asList(1, 4, 5);
        List<Integer> result =
            integers.stream()
                    .map(x -> x + 1)
                    .collect(Collectors.toList());
        assertEquals(result, Arrays.asList(2, 5, 6));
    }

    @Test
    public void testBasicStreamFromSet() {
        Set<Integer> integers = Set.of(30, 10, 12, 4);
        Set<Integer> result =
            integers.stream()
                    .map(x -> x + 1)
                    .collect(Collectors.toSet());
        assertEquals(result, Set.of(31, 11, 13, 5));
    }

    @Test
    public void testBasicStreamFromMap() {
        Map<String, String> countriesAndCapitals =
            Map.of("Denmark", "Copenhagen", "China", "Beijing");

        String result = countriesAndCapitals
            .entrySet()
            .stream()
            .map(Map.Entry::getValue)
            .collect(Collectors.joining(","));

        assertThat(result).contains("Beijing", "Copenhagen");
    }

    @Test
    public void testBasicStreamFromArray() {
        String[] stringArray = new String[]{"Foo","Bar","Baz","Bam"};
        List<String> result =
            Arrays.stream(stringArray)
                    .map(x -> x + "!")
                    .collect(Collectors.toList());
        assertEquals(result, List.of("Foo!", "Bar!", "Baz!", "Bam!"));
    }

    @Test
    public void testSpecializedIntStream() {
        int sum = IntStream.of(30, 12, 50).map(x -> x * 3).sum();
    }

    @Test
    public void testSpecializedLongStream() {
        long sum = LongStream.of(30, 12, 50).map(x -> x * 3).sum();
    }

    @Test
    public void testSpecializedDoubleStream() {
        double sum = DoubleStream.of(30, 12, 50).map(x -> x * 3).sum();
    }

    @Test
    public void testBasicStreamFromArrayButWithPrimitive() {
        int[] primitiveIntArray = new int[]{1,3,4,5};
        List<Integer> result =
            Arrays.stream(primitiveIntArray)
                  .map(x -> x + 1)
                  .mapToObj(Integer::valueOf)
                  .collect(Collectors.toList());
        assertEquals(result, Arrays.asList(2, 4, 5, 6));
    }

    @Test
    public void testBasicStreamFromArrayButWithPrimitiveWithBoxed() {
        int[] primitiveIntArray = new int[]{1,3,4,5};
        List<Integer> result =
            Arrays.stream(primitiveIntArray)
                  .map(x -> x + 1)
                  .boxed()
                  .collect(Collectors.toList());
        assertEquals(result, Arrays.asList(2, 4, 5, 6));
    }

    @Test
    public void testConvertGeneralStreamToSpecializedStream() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer sum =
            numbers.stream()
                   .map(x -> x + 1)
                   .mapToInt(value -> value)
                   .sum();
        Integer sum2 =
            numbers.stream()
                   .map(x -> x + 1)
                   .collect(Collectors.summingInt(x -> x));
    }


    @Test
    public void testBasicCollector() {
        List<Integer> numbers =
            Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> result =
            numbers.stream()
                   .map(x -> x + 1)
                   .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Ending with the result = " + result);
    }

    @Test
    public void testBasicParallelCollector() {
        List<Integer> numbers =
            Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> result =
            numbers.stream()
                   .parallel()
                   .map(x -> {
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
                    System.out.format("%s: left = %s\n",
                        Thread.currentThread().getName(), left);
                    System.out.format("%s: right = %s\n",
                        Thread.currentThread().getName(), right);
                    left.addAll(right);
                    System.out.format("%s: combined = %s\n",
                        Thread.currentThread().getName(), left);
                });
        System.out.println("Ending with the result = " + result);
        assertEquals(result, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    public void testBasicParallelCollectorCleaned() {
        List<Integer> numbers =
            Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> result =
            numbers.stream()
                   .parallel()
                   .map(x -> x + 1)
                   .collect(ArrayList::new,
                       ArrayList::add,
                       ArrayList::addAll);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), result);
    }


    @Test
    public void testSpecializedIntStreamRange() {
        ArrayList<Integer> result = IntStream.range(0, 5).collect(
            ArrayList::new,
            List::add,
            List::addAll
        );
        assertThat(result).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    public void testSpecializedDoubleStreamIterate() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        StringJoiner result = DoubleStream
            .iterate(1.0, x -> x + .05)
            .limit(3)
            .collect(() -> new StringJoiner(", ", "[", "]"),
                (stringJoiner, value) -> stringJoiner.add(decimalFormat.format(value)),
                StringJoiner::merge);
        assertThat(result.toString()).isEqualTo("[1.00, 1.05, 1.10]");
    }

    @Test
    public void testSpecializedLongStreamWithBuilderAndAdd() {
        LongStream longStream =
            LongStream.builder().add(40L).add(10L).add(20L).build();
        longStream
            .average()
            .ifPresentOrElse(avg -> System.out.printf("Average: %2.2f", avg),
                () -> System.out.println("No Average"));
    }

    @Test
    public void testSpecializedLongStreamWithBuilderAndAccept() {
        LongStream.Builder builder =
            LongStream.builder().add(40L).add(10L).add(20L);
        builder.accept(31L);
        builder.accept(41L);
        builder.accept(51L);
        builder.accept(61L);
        LongStream longStream = builder.build();
    }

    @Test
    public void testLongStreamBuilder() {
        LongStream longStream =
            LongStream.builder()
                      .add(40L)
                      .add(10L)
                      .add(20L)
                      .build();
        longStream
            .average()
            .ifPresentOrElse(
                avg -> System.out.printf("Average: %2.2f", avg),
                () -> System.out.println("No Average"));
    }

    @Test
    public void convertToSpecializedStream() {
        Stream.of(1,2,3,4,5).mapToInt(x -> x).sum();
    }

    @Test
    public void testMapToInt() {
        //LongStream.iterate(0, x -> x + 1)
    }

    @Test
    public void testMapToLong() {

    }

    @Test
    public void testMapToDouble() {

    }

    @Test
    public void testBoxed() {

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
        Stream<Integer> integerStream = Stream.iterate(0, x -> x + 1); //Goes
        // on forever!
        List<Integer> result = integerStream.map(x -> x + 4)
                                            .parallel()
                                            .peek(x -> System.out.format("%s:" +
                                                    " element %s\n",
                                                Thread.currentThread().getName(), x))
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
                                              .flatMap(x -> Stream.of(x,
                                                  x.plusYears(10)))
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
            Stream.of("Apple", "Orange", "Banana", "Tomato", "Grapes", "Plum"
                , "Kiwi");
        System.out.println(stream
            .sorted(Comparator
                .comparing(String::length).thenComparing(identity()))
            .collect(Collectors.toList()));
    }

    @Test
    public void testGroupBy() {

    }

    @Test
    public void testGroupingByConcurrent() {
        IntStream stream = IntStream.range(0, 1000);
        Map<Boolean, List<Integer>> groups =
            stream.parallel()
                  .boxed()
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
    @Disabled(value = "Not yet implemented")
    public void testToMapCollector() {

    }

    @Test
    @Disabled(value = "Not yet implemented")
    public void testCollectWithFinisher() {

    }

    @Test
    @Disabled(value = "Not yet implemented")
    public void testMap() {

    }

    @Test
    @Disabled(value = "Not yet implemented")
    public void testFlatMap2() {

    }

    @Test
    @Disabled(value = "Not yet implemented")
    public void testFilter() {

    }

    @Test
    @Disabled(value = "Not implemented yet")
    public void collectorsToMap() {
//
//
//        Map<String, Integer> map = stream1
//            .flatMap(x -> stream2.map(y -> new Pair<>(x, y)))
//            .collect(Collectors.toMap(pair -> pair.first, pair -> pair.second));
//        System.out.println(map);
    }

    @Test
    @Disabled(value = "Not implemented yet")
    public void testFindFirst() {
    }

    @Test
    public void testSummaryStatistics() {

    }
}











