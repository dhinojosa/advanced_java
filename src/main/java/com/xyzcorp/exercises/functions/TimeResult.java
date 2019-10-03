package com.xyzcorp.exercises.functions;

import java.util.Objects;
import java.util.StringJoiner;

public class TimeResult<B> {
    private Long time;
    private B result;

    public TimeResult(Long time, B result) {
        this.time = time;
        this.result = result;
    }

    public B getResult() {
        return result;
    }

    public Long getTime() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeResult<?> that = (TimeResult<?>) o;
        return Objects.equals(time, that.time) &&
            Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, result);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TimeResult.class.getSimpleName() + "[",
            "]")
            .add("time=" + time)
            .add("result=" + result)
            .toString();
    }
}
