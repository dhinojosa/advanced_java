package com.xyzcorp.demos.designpatterns.tdd;

import java.time.LocalDate;

public class ProgrammerFactory {
    public static Programmer create(String firstName, String lastName, LocalDate birthDate) {
        //preconditions
        if (firstName.isEmpty()) throw new RuntimeException("first name is empty");
        return new Programmer(firstName, lastName, birthDate, LocalDate::now);
    }
}
