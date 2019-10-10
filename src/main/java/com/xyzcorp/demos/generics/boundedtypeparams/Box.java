package com.xyzcorp.demos.generics.boundedtypeparams;

import java.util.function.Function;

public class Box <T> {

    private final T t;

    public Box(T t) {
        this.t = t;
    }

    public <U> Box<U> map(Function<T, U> f) {
        return null;
    }
}
