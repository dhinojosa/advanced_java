package com.xyzcorp.exercises.streams;

import java.util.Objects;
import java.util.StringJoiner;

public class Employee {
    private final String firstName;
    private final String lastName;
    private final Integer salary;

    public Employee(String firstName, String lastName, Integer salary) {
        Objects.requireNonNull(firstName, "First Name is null");
        Objects.requireNonNull(lastName, "Last Name is null");
        Objects.requireNonNull(firstName, "Salary is null");
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstName.equals(employee.firstName) &&
            lastName.equals(employee.lastName) &&
            salary.equals(employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, salary);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .add("salary=" + salary)
            .toString();
    }
}
