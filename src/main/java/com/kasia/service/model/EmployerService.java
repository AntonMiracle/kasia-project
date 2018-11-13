package com.kasia.service.model;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Employer;
import com.kasia.service.Service;

import javax.validation.ValidationException;
import java.util.Set;

public interface EmployerService extends Service {
    Employer create(String name) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException, OnUseRunTimeException;

    Employer update(Employer employer) throws ValidationException, IllegalArgumentException;

    Employer getEmployerById(long id) throws IllegalArgumentException;

    Employer getEmployerByName(String name) throws IllegalArgumentException;

    Set<Employer> getAllEmployers();
}
