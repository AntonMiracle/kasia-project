package com.kasia.service;

import com.kasia.model.Employer;

import javax.validation.ValidationException;
import java.util.Set;

public interface EmployerService extends ValidationService<Employer>, Service {
    Employer create(String name) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException;

    Employer update(Employer employer) throws ValidationException, IllegalArgumentException;

    Employer getEmployerById(long id) throws IllegalArgumentException;

    Employer getEmployerByName(String name) throws IllegalArgumentException;

    Set<Employer> getAllEmployers();
}
