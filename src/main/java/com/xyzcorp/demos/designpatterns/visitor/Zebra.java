package com.xyzcorp.demos.designpatterns.visitor;

/**
 * @author John Ericksen
 */
public class Zebra implements Animal {

    public void accept(AnimalVisitor visitor) {
        visitor.visit(this);
    }

    public void feed(Hay hay) {
        System.out.println("Chomp Chomp");
    }
}
