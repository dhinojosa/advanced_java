package com.xyzcorp.demos.designpatterns.adapter;

/**
 * @author John Ericksen
 */
public class CelciusThermometer {

    private double temp;

    public CelciusThermometer(double temp) {
        this.temp = temp;
    }

    public double getTemperateInC() {
        return temp;
    }
}
