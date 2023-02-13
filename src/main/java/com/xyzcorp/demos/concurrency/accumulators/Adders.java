package com.xyzcorp.demos.concurrency.accumulators;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;

public class Adders {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LongAdder longAdder = new LongAdder();  //Contended
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> submit1 = executorService.submit(() -> {
            for (int i = 0; i < 100; i++) {
                longAdder.add(i);
            }
        });

        Future<?> submit = executorService.submit(() -> {
            for (int i = 200; i < 300; i++) {
                longAdder.add(i);
            }
        });


        submit.get();
        submit1.get();

        System.out.println(longAdder.sum());
        executorService.shutdown();
    }
}
