package com.kasia.model.validation.imp;

import com.kasia.model.Budget;
import com.kasia.model.validation.BudgetValidationService;
import com.kasia.model.validation.FieldName;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class BudgetValidationServiceImp implements BudgetValidationService {
    @Override
    public boolean isNameValid(String name) {
        Budget budget = new Budget();
        budget.setName(name);

        Validator validator = getValidator();
        for (ConstraintViolation<Budget> violation : validator.validate(budget)) {
            if (violation.getPropertyPath().toString().equals(FieldName.USER_NAME.getName())) {
                return false;
            }
        }
        return true;
    }
}
