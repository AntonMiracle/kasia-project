package com.kasia.model.service.imp;

import com.kasia.model.Element;
import com.kasia.model.ElementProvider;
import com.kasia.model.Operation;
import com.kasia.model.Price;
import com.kasia.model.repository.OperationRepository;
import com.kasia.model.service.OperationService;
import com.kasia.model.validation.OperationValidationService;
import com.kasia.model.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OperationServiceImp implements OperationService, OperationValidationService {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private ValidationService<Operation> operationValidationService;

    @Override
    public Operation save(Operation model) {

        return null;
    }

    @Override
    public boolean delete(Operation model) {
        return false;
    }

    @Override
    public Operation findById(long id) {
        return null;
    }

    @Override
    public Set<Operation> findAll() {
        return null;
    }

    @Override
    public Operation create(Element element, Price price, ElementProvider elementProvider) {
        return null;
    }
}
