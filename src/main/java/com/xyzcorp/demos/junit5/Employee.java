package com.xyzcorp.demos.junit5;

public class Employee {
    private String firstName;
    private String lastName;

    public Employee(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
