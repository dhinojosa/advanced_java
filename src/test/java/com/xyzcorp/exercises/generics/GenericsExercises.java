package com.xyzcorp.exercises.generics;

import com.xyzcorp.demos.generics.people.American;
import com.xyzcorp.demos.generics.people.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericsExercises {

    @Test
    public void testStaticFactory() {
        Box<Integer> box = Box.of(40);
        assertThat(box.getContent()).isEqualTo(40);
    }

    @Test
    public void testMapBox() {
        Box<String> bs = Box.of("Hello"); //Box<String>
        Box<Integer> transformed = bs.map(String::length);
        System.out.println(transformed.getContent() == 5);
    }

    @Test
    public void testMapBoxWithAlternateTypes() {
        Box<String> bs = Box.of("Hello"); //Box<String>

        Function <CharSequence, Person> fun = new Function<CharSequence, Person>(){
            @Override
            public American apply(CharSequence charSequence) {
                return new American();
            }
        };

        //The above function will fit, that's the flexibility that we gain!
        Box<Person> map = bs.map(fun);

        Person person = map.getContent();
        System.out.println(person);
    }

}
