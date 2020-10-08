package com.xyzcorp.demos.generics;

import com.xyzcorp.demos.generics.people.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings(value = {"WeakerAccess", "unused"})
public class CovariantTest {
    public void processCovariantList(List<? extends NorthAmerican> northAmericansOrLower) {
        Object object = northAmericansOrLower.get(0);
        Person person = northAmericansOrLower.get(0);
        NorthAmerican northAmerican = northAmericansOrLower.get(0);

        //at the expense of losing it here.
        //northAmericansOrLower.add(new Object());
        //northAmericansOrLower.add(new NorthAmerican());
        //northAmericansOrLower.add(new American());
        //northAmericansOrLower.add(new Minneapolitan());
        //northAmericansOrLower.add(new NewMexican());
        //northAmericansOrLower.add(new Bostonian());
        //northAmericansOrLower.add(new NorthAmerican());

        //only a null
        northAmericansOrLower.add(null);
    }

    /*
     *
     * Space is intentional
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

    //11. Process Covariant List
    @Test
    public void testCovariantAmericanList() {
        //I gain flexibility here
        List<Missourian> missourians = new ArrayList<>();
        missourians.add(new StLouisan());
        missourians.add(new StLouisan());
        missourians.add(new StLouisan());
        missourians.add(new StLouisan());
        missourians.add(new Missourian());
        processCovariantList(missourians);

        List<NorthAmerican> northAmericans = new ArrayList<>();
        northAmericans.add(new American());
        northAmericans.add(new Canadian());
        northAmericans.add(new NorthAmerican());
        northAmericans.add(new Mexican());
        processCovariantList(northAmericans);

        List<Minneapolitan> minneapolitans = new ArrayList<>();
        minneapolitans.add(new Minneapolitan());
        minneapolitans.add(new Minneapolitan());
        minneapolitans.add(new Minneapolitan());
        minneapolitans.add(new Minneapolitan());
        minneapolitans.add(new Minneapolitan());
        processCovariantList(minneapolitans);
    }

    //12. Process Covariant Assignment
    @SuppressWarnings("UnnecessaryLocalVariable")
    @Test
    public void testCovariantAssignment() {
        List<Bostonian> bostonians = new ArrayList<>();
        bostonians.add(new Bostonian());
        bostonians.add(new Bostonian());
        bostonians.add(new Bostonian());
        bostonians.add(new Bostonian());

        List<? extends American> americansOrLower = bostonians;
        Object object = americansOrLower.get(0);
        Person person = americansOrLower.get(0);
        NorthAmerican northAmerican = americansOrLower.get(0);
        American american = americansOrLower.get(0);

        //    Massachusettsan massachusettsan= americansOrLower.get(0);
        //    Bostonian bostonian = americansOrLower.get(0);

//        americansOrLower.add(new Object());
//        americansOrLower.add(new Person());
//        americansOrLower.add(new NorthAmerican());
//        americansOrLower.add(new American());
//        americansOrLower.add(new Massachusettsan());
//        americansOrLower.add(new Bostonian());
        americansOrLower.add(null); //special case
    }

    public void processCovariantPersonList(List<? extends Person> people) {
        Object object = people.get(0);
        Person person = people.get(0);
        people.add(null);
    }

    public void processUnbounded(List<?> list) {
        Object o = list.get(0);
        list.set(4, null);
        list.add(null);
    }

    //13. Process Covariant Assignment
    @Test
    public void testCovariantPersonList() {
        List<Dallasite> dallasites = new ArrayList<>();
        dallasites.add(new Dallasite());
        dallasites.add(new Dallasite());
        processCovariantPersonList(dallasites);
    }

    //15. Process Covariant Assignment with Object List
    @Test
    public void testProcessCovariantObjectList() {
        List<Object> objects = new ArrayList<>();
        objects.add(new Denverite());
        objects.add(new Floridian());
        processCovariantObjectList(objects);


        @SuppressWarnings("UnnecessaryLocalVariable")
        List<?> objects2 = objects;
        processCovariantObjectList(objects2);
    }

    //14. Process Covariant Assignment
    @SuppressWarnings("TypeParameterExplicitlyExtendsObject")
    public void processCovariantObjectList(
            List<? extends Object> people) {
        Object object = people.get(0);
        //Person person = people.get(0);
        //Bostonian bostonian = people.get(0);

        people.add(null);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Test
    public void testMixMatchedPersonList() {
        List<Bostonian> bostonians = new ArrayList<>();
        bostonians.add(new Bostonian());
        bostonians.add(new Bostonian());
        bostonians.add(new Bostonian());
        List<? extends Bostonian> bostoniansOrLower = bostonians;
        List<? extends American> people = bostoniansOrLower;
        processCovariantPersonList(people);
    }
}
