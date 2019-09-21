package com.xyzcorp.demos.generics;

import com.xyzcorp.Box;
import com.xyzcorp.demos.generics.people.*;
import org.junit.Test;

import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class GenericsTest {
    @Test
    public void testBoxing() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        System.out.println(list.get(0) + 1);
    }

    @Test
    public void testUsingBox() {
        Box<Integer> box = new Box<>(4);
        Box<String> box2 = new Box<>("Howdy");
        Box<List<Integer>> box3 = new Box<>(Arrays.asList(1, 2, 3, 4, 5));
        Box<Box<Box<Integer>>> box5 = new Box<>(new Box<>(new Box<>(10)));
        System.out.println(box5.getContents().getContents().getContents());
    }

    @Test
    public void testInvariance() {
        //Call by site
        Box<Californian> boxOfCalifornians = new Box<Californian>();
        //Setters OK
        boxOfCalifornians.setContents(new Californian());
        //Getters OK
        Californian californian = boxOfCalifornians.getContents();
        System.out.println("boxOfCalifornians = " + boxOfCalifornians);
    }

    @Test
    public void testCovarianceAssignments() {
        Box<NorthernCalifornian> boxOfNorthernCalifornians = new Box<>();
        Box<? extends Californian> boxOfCalifornians = boxOfNorthernCalifornians;
        Box<? extends Object> boxOfObjects = boxOfNorthernCalifornians;
        Box<?> boxOfObjects2 = boxOfNorthernCalifornians;
        Box<? extends Person> boxOfPeople = boxOfNorthernCalifornians;
        Box<? extends American> boxOfAmericans = boxOfNorthernCalifornians;
        Box<? extends NorthernCalifornian> boxOfNorthernCalifornians2 = boxOfNorthernCalifornians;

        //Go over this
        Box<? extends NorthernCalifornian> box3 = new Box<Milpitasan>();
        NorthernCalifornian contents = box3.getContents();

        //Box<? extends SanFranciscan> boxOfSanFranciscans  = boxOfNorthernCalifornians;
    }

    @Test
    public void testCovarianceGetPrinciple() {
        Box<SanFranciscan> boxOfSanFranciscans = new Box<>();
        Box<? extends Californian> californians = boxOfSanFranciscans;

        Object object = californians.getContents();
        Person person = californians.getContents();
        American american = californians.getContents();
        Californian californian = californians.getContents();
        californians.setContents(null);
        //NorthernCalifornian northernCalifornians = californians.getContents();
    }

    @Test
    public void testContravarianceAssignments() {
        Box<Californian> boxOfCalifornians = new Box<>();

        //This is an attempt at contravariance, this will not work
        //Box<? super Object> boxOfObjects = boxOfCalifornians;
        //Box<? super Person> boxOfPeople = boxOfCalifornians;
        //Box<? super American> boxOfAmericans = boxOfCalifornians;

        Box<? super Californian> boxOfCalifornians1 = boxOfCalifornians;


        // We are talking about this assignment
        Box<? super NorthernCalifornian> northernCalifornians = boxOfCalifornians;
        northernCalifornians.setContents(new SanFranciscan());
        northernCalifornians.setContents(new Milpitasan());
        northernCalifornians.setContents(new NorthernCalifornian());

        //northernCalifornians.setContents(new Californian());
        //NorthernCalifornian northernCalifornian1 = northernCalifornians.getContents();
        //Californian californian1 = northernCalifornians.getContents();
        //American american = northernCalifornians.getContents();
        //Person person = northernCalifornians.getContents();
        Object object = northernCalifornians.getContents();
    }
    
    public <E> Optional<E> findFirst(List<? extends E> list) {
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.get(0));
    }

    @Test
    public void testVariancesInMethod() {
        List<Integer> items = Arrays.asList(5,10, 12, 10, 19, 44);
        assertEquals(Optional.of(5), findFirst(items));
    }

    @Test
    public void testMap() throws Exception {
        Box<Integer> box = new Box<>(4);
        Box<String> newBox = box.map(integer ->
                Stream.generate(() -> "Wow")
                      .limit(integer)
                      .collect(Collectors.joining()));
        System.out.println("newBox = " + newBox);
    }

    @Test
    public void testMapWithPeople() throws Exception {
        Box<Californian> box = new Box<>();
        Californian californian = new Californian();
        californian.setEyeColor(EyeColor.HAZEL);
        californian.setFirstName("Travis");
        californian.setLastName("Kalenik");
        box.setContents(californian);
        Box<EyeColor> eyeColorBox = box.map(Person::getEyeColor);
        System.out.println(eyeColorBox.getContents().toString());
    }

    public <T extends Appendable & Flushable & Closeable> void foo(T t) throws IOException {
        t.append('c');
        t.append('d');
        t.flush();
        t.close();
    }

    public Method[] listMethodsFromRawClass(Class<Person> clazz) {
        return clazz.getMethods();
    }

    @Test
    public void testIntrospection() {
        Method[] methods = listMethodsFromRawClass(Person.class);
//        Method[] methods2 = listMethodsFromRawClass(Object.class);
//        Method[] methods3 = listMethodsFromRawClass(String.class);
    }

    @Test
    public void testMultipleInheritance() throws IOException {
        CharArrayWriter writer = new CharArrayWriter(40);
        foo(writer);
        System.out.println(writer.toCharArray());
    }
}




















