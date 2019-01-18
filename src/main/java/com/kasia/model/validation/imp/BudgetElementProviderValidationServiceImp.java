package com.kasia.model.validation.imp;

import com.kasia.model.BudgetElementProvider;
import com.kasia.model.ElementProvider;
import com.kasia.model.Model;
import com.kasia.model.validation.BudgetElementProviderValidationService;
import com.kasia.model.validation.BudgetValidationService;
import com.kasia.model.validation.ElementProviderValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class BudgetElementProviderValidationServiceImp implements BudgetElementProviderValidationService {
    @Autowired
    private BudgetValidationService budgetValidationService;
    @Autowired
    private ElementProviderValidationService providerValidationService;

    @Override
    public boolean isValid(BudgetElementProvider model) {
        if (model == null) return false;

        if (!budgetValidationService.isValid(model.getBudget()) || model.getElementProviders() == null) return false;
        for (ElementProvider provider : model.getElementProviders()) {
            if (!providerValidationService.isValid(provider)) return false;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }
}
