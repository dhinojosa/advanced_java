package com.xyzcorp.demos.designpatterns.visitor;

/**
 * @author John Ericksen
 */
public interface Animal {

    public void accept(AnimalVisitor visitor);
}
