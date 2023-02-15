package com.xyzcorp.demos.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

//Source: https://blog.avenuecode.com/java-microbenchmarks-with-jmh-part-2

public class MyBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"1000000", "10000000", "100000000"})
        public int listSize;

        public List<Integer> testList;

        @Setup(Level.Trial)
        public void setUp() {
            testList = new Random()
                    .ints()
                    .limit(listSize)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }

    public static class VolatileLong {
        private volatile long value = 0;

        public synchronized void add(long amount) {
            this.value += amount;
        }

        public long getValue() {
            return this.value;
        }
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void longAdder(Blackhole blackhole, BenchmarkState state) {
        LongAdder adder = new LongAdder();
        state.testList.parallelStream().forEach(adder::add);
        blackhole.consume(adder.sum());
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void atomicLong(Blackhole blackhole, BenchmarkState state) {
        AtomicLong atomicLong = new AtomicLong();
        state.testList.parallelStream().forEach(atomicLong::addAndGet);
        blackhole.consume(atomicLong.get());
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void volatileLong(Blackhole blackHole, BenchmarkState state) {
        VolatileLong volatileLong = new VolatileLong();
        state.testList.parallelStream().forEach(volatileLong::add);
        blackHole.consume(volatileLong.getValue());
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void longStreamSum(Blackhole blackHole, BenchmarkState state) {
        long sum = state.testList.parallelStream().mapToLong(s -> s).sum();
        blackHole.consume(sum);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
