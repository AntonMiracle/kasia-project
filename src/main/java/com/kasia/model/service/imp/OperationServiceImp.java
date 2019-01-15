package com.kasia.model.service.imp;

import com.kasia.model.Element;
import com.kasia.model.ElementProvider;
import com.kasia.model.Operation;
import com.kasia.model.Price;
import com.kasia.model.service.OperationService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImp implements OperationService, ValidationService<Operation> {
    @Override
    public Operation save(Operation model) {
        return null;
    }

    @Override
    public boolean delete(Operation model) {
        return false;
    }

    @Override
    public Operation getById(long id) {
        return null;
    }

    @Override
    public Operation create(Element element, Price price, ElementProvider elementProvider) {
        return null;
    }
}
