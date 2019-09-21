package com.xyzcorp.demos.designpatterns.factorymethod.example;

public class OlympicMedalGiver {

    public void provideMedal(Country country) {
        System.out.println("I am provide a gold to " + country.getName());
    }
}
