package com.xyzcorp.demos.designpatterns.factorymethod.example;

public class Dollar implements Currency {
    private final long pennies;

    public Dollar(long pennies) {
        this.pennies = pennies;
    }

    @Override
    public long amountInDollars() {
        return this.pennies / 100;
    }
}
