package com.kasia.service.imp;

import com.kasia.model.Employer;
import com.kasia.repository.EmployerRepository;
import com.kasia.service.EmployerService;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import java.util.Set;

public class EmployerServiceImp implements EmployerService {
    @EJB
    private EmployerRepository employerRepository;

    @Override
    public Employer create(String name) throws ValidationException {
        Employer employer = new Employer(name);
        employer.setDescription("");
        if (!isValid(employer)) throw new ValidationException();
        long id = employerRepository.save(employer).getId();
        return employerRepository.getById(id);
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        Employer employer = employerRepository.getById(id);
        return employer == null || employerRepository.delete(employer);
    }

    @Override
    public Employer update(Employer employer) throws ValidationException, IllegalArgumentException {
        if (!isValid(employer)) throw new ValidationException();
        if (employer.getId() == 0) throw new IllegalArgumentException();
        employerRepository.save(employer);
        return employerRepository.getById(employer.getId());
    }

    @Override
    public Employer getEmployerById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return employerRepository.getById(id);
    }

    @Override
    public Employer getEmployerByName(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException();
        return employerRepository.getByName(name);
    }

    @Override
    public Set<Employer> getAllEmployers() {
        return employerRepository.getAll();
    }
}
