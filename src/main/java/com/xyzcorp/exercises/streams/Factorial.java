package com.xyzcorp.exercises.streams;

import java.util.stream.IntStream;

public class Factorial {
    public static int factorial(int i) {
        return IntStream.range(1, i+1)
                        .reduce((left, right) -> left * right)
                        .orElse(-1);
    }
}
