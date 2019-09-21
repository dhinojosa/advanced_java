package com.xyzcorp.demos.designpatterns.factorymethod.example;

public interface CountryFactory {
    public Country createCountry(String name, byte[] flag, String capital);
}
