package com.xyzcorp.demos.functions;

import java.util.Optional;

public class MyCollection<T extends Comparable<T>> {
    private final T[] items;

    public MyCollection(T... items) { //varargs
        this.items = items;
    }

    public Optional<T> max() {
        if (items.length == 0) return Optional.empty();
        T result = items[0];
        for (T item : items) {
            if (item.compareTo(result) > 0) result = item;
        }
        return Optional.of(result);
    }
}