package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.validator.constraint.BudgetNameIsValid;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.BudgetValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BudgetNameValidator implements ConstraintValidator<BudgetNameIsValid, Object> {
    @Autowired
    private UserService uService;
    @Autowired
    private BudgetValidation bValidator;
    private String nameFN;
    private ValidatorUtil vUtil = new ValidatorUtil();

    @Override
    public void initialize(BudgetNameIsValid constraintAnnotation) {
        nameFN = constraintAnnotation.nameFN();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext cvContext) {
        String value = vUtil.findStringValue(o, nameFN);
        User user = uService.findUserByEmail(vUtil.findStringValue(o,"userEmail"));

        if(user == null || value == null || value.length() ==0 || !bValidator.isNameValid(value)
                || uService.findOwnBudgets(user.getId()).stream().filter(budget -> budget.getName().equals(value)).count() != 0 ){
            vUtil.addConstraintViolation(nameFN, cvContext.getDefaultConstraintMessageTemplate(), cvContext);
            return false;
        }

        return true;
    }
}