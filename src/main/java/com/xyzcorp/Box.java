package com.xyzcorp;

import java.util.function.Function;

public class Box<A> {
    private A contents;

    public Box() {
    }

    public Box(A contents) {
        this.contents = contents;
    }

    public void setContents(A contents) {
        this.contents = contents;
    }

    public A getContents() {
        return contents;
    }

    public <R> Box<R> map(Function<? super A, ? extends R> f) {
        return new Box<>(f.apply(contents));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof Box)) return false;
        else return equals((Box<A>) o);
    }

    public boolean equals(Box<A> box) {
        return this.contents.equals(box.contents);
    }

    @Override
    public String toString() {
        return "Box[" + contents.toString() + "]";
    }

    @Override
    public int hashCode() {
        //Store constant non-zero value and store onto contents `result`
        return 449 * contents.hashCode();
    }
}