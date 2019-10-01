package com.xyzcorp.exercises.junit5;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindSmallestIntegerToZero {

    public static final String CANNOT_ACCEPT_NULL_AS_AN_ARGUMENT_MSG = "Cannot accept null as an argument";

    public static Optional<Integer> findSmallestIntegerToZero(List<Integer> list) {
        Objects.requireNonNull(list, CANNOT_ACCEPT_NULL_AS_AN_ARGUMENT_MSG);
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(Collections.min(list.stream().map(Math::abs).collect(Collectors.toSet())));
    }
}
