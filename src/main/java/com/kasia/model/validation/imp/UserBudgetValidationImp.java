package com.kasia.model.validation.imp;

import com.kasia.model.Budget;
import com.kasia.model.Model;
import com.kasia.model.UserBudget;
import com.kasia.model.validation.BudgetValidation;
import com.kasia.model.validation.UserBudgetValidation;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class UserBudgetValidationImp implements UserBudgetValidation {
    @Autowired
    private BudgetValidation budgetValidationService;
    @Autowired
    private UserValidation userValidationService;

    @Override
    public boolean isValid(UserBudget model) {
        if (model == null) return false;

        if (!userValidationService.isValid(model.getUser()) || model.getBudgets() == null) return false;

        for (Budget budget : model.getBudgets()) {
            if (!budgetValidationService.isValid(budget)) return false;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }
}
