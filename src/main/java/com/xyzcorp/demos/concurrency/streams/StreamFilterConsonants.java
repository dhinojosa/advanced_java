package com.xyzcorp.demos.concurrency.streams;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFilterConsonants {
    public static void main(String[] args) {
        Map<Boolean, Integer> result =
            Stream.of(1, 2, 3, 4, 5)
                  .map(x -> x * 5)
                  .parallel()
                  .collect(Collectors.groupingBy(integer -> integer % 2 == 0))
                  .entrySet()
                  .stream()
                  .map(e -> Map.entry(e.getKey(),
                      e.getValue().stream().mapToInt(i -> i).sum()))
                  .collect(Collectors.toMap(Map.Entry::getKey,
                      Map.Entry::getValue));

        System.out.println(result);

    }
}
