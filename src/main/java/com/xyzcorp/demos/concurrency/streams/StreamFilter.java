package com.xyzcorp.demos.concurrency.streams;

import java.util.List;
import java.util.stream.Collectors;

public class StreamFilter {
    public static void main(String[] args) {
        String collect =
            "Follow me to the rainbow"
                .chars()
                .filter(c -> !List.of('a', 'e', 'i', 'o', 'u').contains((char) c))
                .filter(c -> c != ' ')
                .boxed()
                .map(i -> String.valueOf((char) i.intValue()))
                .collect(Collectors.joining());
        System.out.println(collect);
    }
}
