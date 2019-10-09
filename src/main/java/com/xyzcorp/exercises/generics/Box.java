package com.xyzcorp.exercises.generics;

import java.util.function.Function;

public class Box<E> {
    private final E e;

    private Box(E e) {
        this.e = e;
    }

    public static <G> Box<G> of(G g) {
        return new Box<>(g);
    }

    public E getContent() {
        return e;
    }

    public <F> Box<F> map(Function<? super E, ? extends F> function) {
        F result = function.apply(e);
        return new Box<>(result);
    }
}
