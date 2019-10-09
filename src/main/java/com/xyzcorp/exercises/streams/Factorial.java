package com.xyzcorp.exercises.streams;

import java.util.stream.IntStream;

public class Factorial {
    public static int factorial(int i) {
        return IntStream.rangeClosed(1, i)
                        .reduce((left, right) -> left * right)
                        .orElse(-1);
    }
}
