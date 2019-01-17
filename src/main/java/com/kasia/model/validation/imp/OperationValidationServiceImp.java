package com.kasia.model.validation.imp;

import com.kasia.model.Model;
import com.kasia.model.Operation;
import com.kasia.model.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class OperationValidationServiceImp implements OperationValidationService {
    @Autowired
    private UserValidationService userValidationService;
    @Autowired
    private ElementProviderValidationService providerValidationService;
    @Autowired
    private ElementValidationService elementValidationService;
    @Autowired
    private PriceValidationService priceValidationService;

    @Override
    public boolean isValid(Operation model) {
        if (model == null) return false;

        if (!userValidationService.isValid(model.getUser())) return false;
        if (!providerValidationService.isValid(model.getElementProvider())) return false;
        if (!elementValidationService.isValid(model.getElement())) return false;
        if (!priceValidationService.isValid(model.getPrice())) return false;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }
}
