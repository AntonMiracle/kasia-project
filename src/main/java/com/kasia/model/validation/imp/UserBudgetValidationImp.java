package com.kasia.model.validation.imp;

import com.kasia.model.Budget;
import com.kasia.model.Model;
import com.kasia.model.UserBudget;
import com.kasia.model.validation.UserBudgetValidation;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class UserBudgetValidationImp implements UserBudgetValidation {

    @Override
    public boolean isValid(UserBudget model) {
        if (model == null) return false;

        if ( model.getBudgets() == null) return false;

        for (Budget budget : model.getBudgets()) {
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }
}
