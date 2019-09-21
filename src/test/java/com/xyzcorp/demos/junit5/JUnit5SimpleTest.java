package com.xyzcorp.demos.junit5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnit5SimpleTest {
    @Test
    public void myFirstTest() {
        assertEquals(2, 1 + 1);
    }
}
