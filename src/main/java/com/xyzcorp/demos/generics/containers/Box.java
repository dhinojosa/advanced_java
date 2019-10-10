package com.xyzcorp.demos.generics.containers;

import java.util.Objects;
import java.util.function.Function;

public class Box<E> {
    private E e;

    public Box(E e) {
        this.e = e;
    }

    public static <T> Box<T> of(T i) {
        return new Box<>(i);
    }

    public <M> Box<M> map(Function<? super E, ? extends M> func) {
        M apply = func.apply(this.e);
        return new Box<M>(apply);
    }

    public E getContent() {
        return e;
    }

    public void setContent(E e) {
        this.e = e;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Box{");
        sb.append("e=").append(e);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box<?> box = (Box<?>) o;
        return Objects.equals(e, box.e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e) % 49;
    }
}
