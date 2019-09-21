package com.xyzcorp.demos.designpatterns.factorymethod.example;

public class ConcreteCountryFactory implements CountryFactory {

    @Override
    public Country createCountry(String name, byte[] flag, String capital) {
        return new ConcreteCountry(name, flag, capital);
    }
}
