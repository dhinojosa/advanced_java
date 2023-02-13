package com.xyzcorp.demos.concurrency.streams;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;


/**
 * Source: Java 8 in Action:
 * Lambdas, streams, and functional-style programming
 * queue
 * By Raoul-Gabriel Urma, Mario Fusco, and Alan Mycroft
 */
public class StreamMeasuring {

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                     .limit(n)
                     .parallel()
                     .reduce(0L, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                         .parallel()
                         .reduce(0L, Long::sum);
    }

    public static long benchmark(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    public static void main(String[] args) {
        System.out.println("Iterative sum done in: " +
            benchmark(StreamMeasuring::iterativeSum, 10_000_000) + " msecs");
        System.out.println("Parallel sum done in: " +
            benchmark(StreamMeasuring::parallelSum, 10_000_000) + " msecs");
        System.out.println("Ranged sum done in: " +
            benchmark(StreamMeasuring::rangedSum, 10_000_000) + " msecs");
        System.out.println("Parallel Ranged sum done in: " +
            benchmark(StreamMeasuring::parallelRangedSum, 10_000_000) + " msecs");
    }
}
