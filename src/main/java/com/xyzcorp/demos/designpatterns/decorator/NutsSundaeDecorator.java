package com.xyzcorp.demos.designpatterns.decorator;

/**
 * @author John Ericksen
 */
public class NutsSundaeDecorator extends SundaeDecorator {
    public NutsSundaeDecorator(Sundae sundae) {
        super(sundae);
    }

    public String describeSundae() {
        return "Nuts " + getDecorated().describeSundae();
    }
}


