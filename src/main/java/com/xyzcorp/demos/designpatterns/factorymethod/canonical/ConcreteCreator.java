package com.xyzcorp.demos.designpatterns.factorymethod.canonical;

public class ConcreteCreator implements Creator{
    public ConcreteProduct factoryMethod() {
       return new ConcreteProduct();
    }
}
