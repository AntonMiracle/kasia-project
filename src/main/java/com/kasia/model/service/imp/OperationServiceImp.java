package com.kasia.model.service.imp;

import com.kasia.model.*;
import com.kasia.model.repository.OperationRepository;
import com.kasia.model.service.ElementProviderService;
import com.kasia.model.service.ElementService;
import com.kasia.model.service.OperationService;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.OperationValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OperationServiceImp implements OperationService {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private OperationValidationService operationValidationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ElementService elementService;
    @Autowired
    private ElementProviderService providerService;

    @Override
    public Operation save(Operation model) {
        operationValidationService.verifyValidation(model);
        operationValidationService.verifyPositiveId(model.getElement().getId());
        operationValidationService.verifyPositiveId(model.getElementProvider().getId());
        operationValidationService.verifyPositiveId(model.getUser().getId());
        return operationRepository.save(model);
    }

    @Override
    public boolean delete(Operation model) {
        operationValidationService.verifyValidation(model);
        operationValidationService.verifyPositiveId(model.getId());
        operationRepository.delete(model);
        return true;
    }

    @Override
    public Operation findById(long id) {
        operationValidationService.verifyPositiveId(id);
        Optional<Operation> operation = operationRepository.findById(id);
        return operation.isPresent() ? operation.get() : null;
    }

    @Override
    public Set<Operation> findAll() {
        Set<Operation> operations = new HashSet<>();
        operationRepository.findAll().forEach(operations::add);
        return operations;
    }

    @Override
    public Operation create(User user, Element element, Price price, ElementProvider elementProvider) {
        Operation operation = new Operation(user, element, elementProvider, price, LocalDateTime.now().withNano(0));
        operationValidationService.verifyValidation(operation);
        return operation;
    }
}
