package com.xyzcorp.exercises.optionals;


import java.util.Objects;
import java.util.StringJoiner;


public class Triple<A, B, C> {
    private final A primary;
    private final B secondary;
    private final C tertiary;

    public Triple(A primary, B secondary, C tertiary) {
        Objects.requireNonNull(primary, "Primary is null");
        Objects.requireNonNull(secondary, "Secondary is null");
        Objects.requireNonNull(tertiary, "Tertiary is null");
        this.primary = primary;
        this.secondary = secondary;
        this.tertiary = tertiary;
    }

    public A getPrimary() {
        return primary;
    }

    public B getSecondary() {
        return secondary;
    }

    public C getTertiary() {
        return tertiary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return primary.equals(triple.primary) &&
            secondary.equals(triple.secondary) &&
            tertiary.equals(triple.tertiary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primary, secondary, tertiary);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Triple.class.getSimpleName() + "[", "]")
            .add("primary=" + primary)
            .add("secondary=" + secondary)
            .add("tertiary=" + tertiary)
            .toString();
    }
}
