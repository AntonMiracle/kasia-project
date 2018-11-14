package com.kasia.service.model.imp;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Employer;
import com.kasia.repository.EmployerRepository;
import com.kasia.service.model.EmployerService;
import com.kasia.service.model.OperationService;
import com.kasia.service.validation.ValidationService;
import com.kasia.service.validation.field.EField;
import com.kasia.service.validation.message.EMessageLink;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.Set;

public class EmployerServiceImp implements EmployerService {
    @Inject
    private EmployerRepository repository;
    @Inject
    private OperationService operationService;
    @Inject
    private ValidationService<Employer,EField,EMessageLink> validationService;

    @Override
    public Employer create(String name) throws ValidationException {
        Employer employer = new Employer(name);
        employer.setDescription("");
        if (!validationService.isValid(employer)) throw new ValidationException();
        long id = repository.save(employer).getId();
        return repository.getById(id);
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        Employer employer = repository.getById(id);
        if (employer == null) return true;
        if (operationService.getOperationsByEmployerId(employer.getId()).size() != 0) throw new OnUseRunTimeException();
        return repository.delete(employer);
    }

    @Override
    public Employer update(Employer employer) throws ValidationException, IllegalArgumentException {
        if (!validationService.isValid(employer)) throw new ValidationException();
        if (employer.getId() == 0) throw new IllegalArgumentException();
        repository.save(employer);
        return repository.getById(employer.getId());
    }

    @Override
    public Employer getEmployerById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getById(id);
    }

    @Override
    public Employer getEmployerByName(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException();
        return repository.getByName(name);
    }

    @Override
    public Set<Employer> getAllEmployers() {
        return repository.getAll();
    }
}
