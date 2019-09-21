package com.xyzcorp.demos.designpatterns.facade;

public interface StudentDAO {
    public Long persist(Student student);
    public Student findById(Long id);
    public boolean isAlreadyRegistered(Student student);
}
