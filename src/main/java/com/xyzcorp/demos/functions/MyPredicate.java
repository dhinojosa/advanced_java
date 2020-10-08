package com.xyzcorp.demos.functions;

//given a x returns true or false
public interface MyPredicate<T> {
    boolean test(T item);
}
