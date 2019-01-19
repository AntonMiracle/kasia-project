package com.kasia.model.validation.imp;

import com.kasia.exception.IdInvalidRuntimeException;
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
public class OperationValidationImp implements OperationValidation {
    @Autowired
    private UserValidation userValidationService;
    @Autowired
    private ElementProviderValidation providerValidationService;
    @Autowired
    private ElementValidation elementValidationService;
    @Autowired
    private PriceValidation priceValidationService;

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

    @Override
    public void verifyPositiveIdInside(Operation operation) throws IdInvalidRuntimeException {
        if (operation.getElementProvider().getId() <= 0) throw new IdInvalidRuntimeException();
        if (operation.getElement().getId() <= 0) throw new IdInvalidRuntimeException();
        if (operation.getUser().getId() <= 0) throw new IdInvalidRuntimeException();
    }
}
