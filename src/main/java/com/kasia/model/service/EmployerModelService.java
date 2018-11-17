package com.kasia.model.service;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Employer;

import javax.validation.ValidationException;
import java.util.Set;

public interface EmployerModelService extends ModelService {
    Employer create(String name) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException, OnUseRunTimeException;

    Employer update(Employer employer) throws ValidationException, IllegalArgumentException;

    Employer getEmployerById(long id) throws IllegalArgumentException;

    Employer getEmployerByName(String name) throws IllegalArgumentException;

    Set<Employer> getAllEmployers();
}
