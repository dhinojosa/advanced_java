package com.xyzcorp.demos.designpatterns.factorymethod.example;

public class NoFlagConcreteCountry implements Country {
    private String name;
    private String capital;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] getFlag() {
        throw new UnsupportedOperationException("Holding flag data too big, won't do it");
    }

    @Override
    public String capital() {
        return capital;
    }
}
