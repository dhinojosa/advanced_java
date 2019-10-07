package com.xyzcorp.demos.generics.containers;

public class MyCollection<T> {
    private final T[] items;

    @SafeVarargs
    public MyCollection(T... items) { //varargs
        this.items = items;
    }
}
