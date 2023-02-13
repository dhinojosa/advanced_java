package com.xyzcorp.demos.concurrency.accumulators;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAccumulator;

public class Accumulators {
    public static void main(String[] args) throws ExecutionException,
        InterruptedException {
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x * y, 1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> submit1 = executorService.submit(() -> {
            for (int i = 1; i < 10; i++) {
                longAccumulator.accumulate(i);
            }
        });

        Future<?> submit = executorService.submit(() -> {
            for (int i = 20; i < 30; i++) {
                longAccumulator.accumulate(i);
            }
        });

        submit.get();
        submit1.get();

        System.out.println(longAccumulator.longValue());
        executorService.shutdown();
    }


}
