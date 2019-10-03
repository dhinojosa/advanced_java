package com.xyzcorp.exercises.streams;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Manager extends Employee {
    private final List<Employee> employees;

    public Manager(String firstName, String lastName, Integer salary,
                   List<Employee> employees) {
        super(firstName, lastName, salary);
        Objects.requireNonNull(employees, "Employees is null");
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Manager manager = (Manager) o;
        return employees.equals(manager.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employees);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Manager.class.getSimpleName() + "[", "]")
            .add("firstName = " + getFirstName())
            .add("lastName = " + getLastName())
            .add("employees=" + employees)
            .toString();
    }
}
