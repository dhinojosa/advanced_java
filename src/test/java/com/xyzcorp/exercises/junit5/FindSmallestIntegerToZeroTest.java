package com.xyzcorp.exercises.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;

public class FindSmallestIntegerToZeroTest {

    private static Stream<Arguments> listAndExpectationProvider() {
        return Stream.of(
            of(Collections.emptyList(), Optional.empty()),
            of(Collections.singletonList(1), Optional.of(1)),
            of(Collections.singletonList(0), Optional.of(0)),
            of(Arrays.asList(1, 2), Optional.of(1)),
            of(Arrays.asList(-1, 1, 2), Optional.of(1)),
            of(Arrays.asList(-40, 1, 2), Optional.of(1)),
            of(Arrays.asList(1, -40, 3, 66), Optional.of(1)),
            of(Arrays.asList(-1, -40, 3, 66), Optional.of(1)),
            of(Arrays.asList(-1, 1), Optional.of(1)),
            of(Arrays.asList(-5, 5), Optional.of(5)),
            of(Arrays.asList(-7, 7), Optional.of(7)),
            of(Arrays.asList(-14, -7, 7, 14), Optional.of(7))
        );
    }

    @ParameterizedTest(name = "{index}: list={0}, expected={1}")
    @MethodSource("listAndExpectationProvider")
    void testFindSmallestIntegerToZeroGivenParams(List<Integer> list,
                                                  Optional<Integer> expected) {
        assertEquals(expected,
            FindSmallestIntegerToZero.findSmallestIntegerToZero(list));
    }

    @Test
    void testThatFindSmallIntegerToZeroCannotAcceptNull() {
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            FindSmallestIntegerToZero.findSmallestIntegerToZero(null);
        });

        assertEquals(FindSmallestIntegerToZero.CANNOT_ACCEPT_NULL_AS_AN_ARGUMENT_MSG, exception.getMessage());
    }
}
