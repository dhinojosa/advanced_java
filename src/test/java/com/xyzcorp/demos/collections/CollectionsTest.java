package com.xyzcorp.demos.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xyzcorp.demos.generics.people.EyeColor;
import com.xyzcorp.demos.generics.people.Person;
import org.junit.Test;

import java.util.*;

public class CollectionsTest {

    @Test
    public void testAlbumCreation() throws Exception {
        Album album = new Album("Led Zeppelin");
        Album album2 = new Album();
    }

    @Test
    public void hashSetMutability() throws Exception {
        Person person = new Person();
        person.setFirstName("Angelina");
        person.setLastName("Jolie");
        person.setEyeColor(EyeColor.BROWN);

        HashSet<Person> hashSet = new HashSet<>();
        hashSet.add(person);

        System.out.println(hashSet.contains(person));

        person.setEyeColor(EyeColor.AMBER);
        System.out.println(hashSet.contains(person));
    }

    class Foo implements Iterator<Integer> {

        private int count = 4;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Integer next() {

            int result = count;
            count = count - 1;
            return result;
        }
    }


    @Test
    public void testUsingMap() {
        Map<String, String> teams = new HashMap<>();
        teams.put("San Francisco", "49ers");
        teams.put("Las Vegas", "Raiders");

        Iterator<Map.Entry<String, String>> iterator = teams.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.format("key: %s, value: %s\n", next.getKey(), next.getValue());
        }
    }

    @Test
    public void usingIterator() {
        Foo foo = new Foo();
        while (foo.hasNext()) {
            Integer next = foo.next();
            System.out.printf("next item: %d\n", next);
        }
    }

    public static class PersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getLastName().compareTo(o2.getLastName());
        }
    }

    @Test
    public void testPriorityQueue() throws Exception {
        Queue<Person> queue = new PriorityQueue<>(new PersonComparator());
        queue.offer(new Person("Franz", "Kafka"));
        queue.offer(new Person("Jane", "Austen"));
        queue.offer(new Person("Leo", "Tolstoy"));
        queue.offer(new Person("Lewis", "Carroll"));
        assert(Optional.ofNullable(queue.peek()).map(Person::getLastName).orElse("").equals("Austen"));
    }

    @Test
    public void testGuavaMultiMap() {
        Multimap<String, Integer> mm = ArrayListMultimap.create();
        mm.put("49ers", 1988);
        mm.put("49ers", 1987);

        System.out.println("mm = " + mm);
    }
}
