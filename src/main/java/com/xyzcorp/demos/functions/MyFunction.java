package com.xyzcorp.demos.functions;

public interface MyFunction<T, R> {
    public R apply(T t);
}
