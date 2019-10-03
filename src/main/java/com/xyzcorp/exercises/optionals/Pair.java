package com.xyzcorp.exercises.optionals;


import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;


public class Pair<K,V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        Objects.requireNonNull(key, "Key is null");
        Objects.requireNonNull(value, "Value is null");
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return key.equals(pair.key) &&
            value.equals(pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pair.class.getSimpleName() + "[", "]")
            .add("key=" + key)
            .add("value=" + value)
            .toString();
    }
}
