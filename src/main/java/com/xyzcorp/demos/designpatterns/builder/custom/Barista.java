package com.xyzcorp.demos.designpatterns.builder.custom;

/**
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 5/29/12
 * Time: 10:44 PM
 */
public class Barista {
    public static void main(String[] args) {
        EspressoDrink drink =
                EspressoDrinkMaker
                        .addShots(2)
                        .addWhip()
                        .addSprinkles()
                        .addSkimMilk()
                        .build();

        System.out.println(drink);
    }
}
