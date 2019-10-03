package com.xyzcorp.exercises.functions;

import java.util.function.Supplier;

public class MyTimer {
    public static <T> TimeResult<T> measureTime(Supplier<T> supplier) {
        long start = System.currentTimeMillis();
        T result = supplier.get();
        long end = System.currentTimeMillis();
        return new TimeResult<>(end - start, result);
    }
}
