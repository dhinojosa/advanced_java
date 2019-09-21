package com.xyzcorp.demos.optionals;

import com.xyzcorp.demos.collections.CollectionsTest;
import com.xyzcorp.demos.generics.people.Person;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OptionalsTest {

    @Test
    public void testOptionalEmpty() {
        Optional<Integer> empty = Optional.empty();
        assertThat(empty).isEmpty();
    }

    @Test
    public void testOptionalNonEmpty() {
        Optional<String> value = Optional.of("Hello");
    }

    @Test
    public void testOptionalFromSomethingPossiblyNull() {
        String possibleNull = getSomethingRandomlyNull();
        Optional<String> optional = Optional.ofNullable(possibleNull);
        if (possibleNull == null) assertThat(optional.isPresent()).isFalse();
        else assertThat(optional.isPresent()).isTrue();
    }

    private String getSomethingRandomlyNull() {
        var random = new java.util.Random();
        if (random.nextBoolean()) {
            return "Foo";
        } else {
            return null;
        }
    }

    @Test
    public void testGettingTheValueTheBadWay() {
        Optional<Long> optionalLong = Optional.empty();
        assertThatThrownBy(optionalLong::get).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testGettingTheValueTheGoodWayUsingGetOrElse() {
        Optional<Long> optionalLong = Optional.of(40L);
        Long result = optionalLong.orElse(-1L);
        assertThat(result).isEqualTo(-1);
    }


    @Test
    public void testGettingTheValueTheGoodWayUsingOrElseGetSupplier() {
        Optional<Long> optionalLong = Optional.of(40L);
        Long result = optionalLong.orElseGet(this::getDefaultAverage);
        assertThat(result).isEqualTo(30L);
    }

    @Test
    public void testPriorityQueue() throws Exception {
        Queue<Person> queue = new PriorityQueue<>(new CollectionsTest.PersonComparator());
        queue.offer(new Person("Franz", "Kafka"));
        queue.offer(new Person("Jane", "Austen"));
        queue.offer(new Person("Leo", "Tolstoy"));
        queue.offer(new Person("Lewis", "Carroll"));
        assert(Optional.ofNullable(queue.peek()).map(Person::getLastName).orElse("").equals("Austen"));
    }

    private Long getDefaultAverage() {
        return 30L;
    }
}
