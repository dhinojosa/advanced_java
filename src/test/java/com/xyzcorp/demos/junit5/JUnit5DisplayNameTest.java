package com.xyzcorp.demos.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnit5DisplayNameTest {

    @Test
    @DisplayName("Custom test name containing spaces")
    public void testWithDisplayNameContainingSpaces() {
    }

    @Test
    @DisplayName("╯°□°）╯")
    public void testWithDisplayNameContainingSpecialCharacters() {
    }

    @Test
    @DisplayName("😱")
    public void testWithDisplayNameContainingEmoji() {
    }
}
