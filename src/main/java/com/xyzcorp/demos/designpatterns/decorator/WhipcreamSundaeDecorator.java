package com.xyzcorp.demos.designpatterns.decorator;

/**
 * @author John Ericksen
 */
public class WhipcreamSundaeDecorator extends SundaeDecorator {
    public WhipcreamSundaeDecorator(Sundae sundae) {
        super(sundae);
    }

    public String describeSundae() {
        return "Whipcream " + getDecorated().describeSundae();
    }
}
