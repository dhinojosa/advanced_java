package com.xyzcorp.exercises.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

public class DateTimeExercises {
    @Test
    void testIterateWithThreeDays() {
        List<LocalDate> firstThreeDaysFromToday =
            Stream.iterate(LocalDate.now(),
                localDate -> localDate.plus(5, ChronoUnit.DAYS))
                  .limit(3).collect(Collectors.toList());
        System.out.println(firstThreeDaysFromToday);
    }

    @Test
    void testWhatDateTimeIsItInBuenosAiresArgentina() {
        System.out.println(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")));
    }

    @Test
    void testFindAllAmericasTimeZones() {
        Stream<Optional<String>> america =
            ZoneId.getAvailableZoneIds().stream().
                filter(s -> s.startsWith("America"))
                  .map(s -> Arrays.stream(s.split("/"))
                                  .reduce((s1, s2) -> s2));
        america.flatMap(Optional::stream).sorted().forEach(System.out::println);
    }

    @Test
    void testFindAllTimeZonesInAntarctica() {
        LocalDateTime now = LocalDateTime.now();
        List<ZonedDateTime> antarcticZones =
            ZoneId.getAvailableZoneIds().stream()  // Stream<String>
                  .filter(regionId -> regionId.contains("Antarctica"))
                  .map(ZoneId::of)  // Stream<ZoneId>
                  .map(now::atZone) // Stream<ZonedDateTime>
                  .sorted(comparingInt(
                      zoneId -> zoneId.getOffset().getTotalSeconds()))
                  .collect(Collectors.toList());

        antarcticZones.forEach(zdt ->
            System.out.printf("%7s: %25s %7s%n", zdt.getOffset(), zdt.getZone(),
                zdt.getZone().getRules().isDaylightSavings(zdt.toInstant())));
    }
}
