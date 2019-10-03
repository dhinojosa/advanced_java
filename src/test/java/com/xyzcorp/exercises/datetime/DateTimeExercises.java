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
import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeExercises {
    @Test
    void testIterateWithThreeDays() {
        List<LocalDate> firstSixEveryThirdDaysOf2018 =
            Stream.iterate(LocalDate.of(2018, 1, 1),
                localDate -> localDate.plus(3, ChronoUnit.DAYS))
                  .limit(6).collect(Collectors.toList());

        assertThat(firstSixEveryThirdDaysOf2018).contains(
            LocalDate.of(2018, 1, 1),
            LocalDate.of(2018, 1, 4),
            LocalDate.of(2018, 1, 7),
            LocalDate.of(2018, 1, 10),
            LocalDate.of(2018, 1, 13),
            LocalDate.of(2018, 1, 16)
        );
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
        america.filter(Optional::isPresent)
               .map(Optional::get).sorted().forEach(System.out::println);
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
