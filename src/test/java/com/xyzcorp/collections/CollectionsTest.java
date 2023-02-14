package com.xyzcorp.collections;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CollectionsTest {
    @Test
    void testHashSetAsNonThreadSafe() {
        new HashSet<Integer>();
        new HashMap<String, Integer>();
    }

    @Test
    void testConcurrentMap() {
        Map<String, Integer> unsynchronizedMap = new HashMap<>();

        ConcurrentHashMap<String, Integer> stringIntegerConcurrentHashMap =
            new ConcurrentHashMap<>(unsynchronizedMap);

        Map<String, Integer> stringIntegerMap =
            Collections.synchronizedMap(unsynchronizedMap);
    }
}
