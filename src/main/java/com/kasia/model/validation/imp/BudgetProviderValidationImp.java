package com.kasia.model.validation.imp;

import com.kasia.model.BudgetProvider;
import com.kasia.model.Provider;
import com.kasia.model.Model;
import com.kasia.model.validation.BudgetProviderValidation;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class BudgetProviderValidationImp implements BudgetProviderValidation {

    @Override
    public boolean isValid(BudgetProvider model) {
        if (model == null) return false;

        if (model.getProviders() == null) return false;
        for (Provider provider : model.getProviders()) {
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }
}
