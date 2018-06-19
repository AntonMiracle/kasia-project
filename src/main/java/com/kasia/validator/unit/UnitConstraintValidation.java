package com.kasia.validator.unit;

import com.kasia.model.unit.Unit;
import com.kasia.validator.ConstraintValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnitConstraintValidation implements ConstraintValidator<UnitConstraint, Unit>, ConstraintValidation<Unit> {
    private int nameMinSize;
    private int nameMaxSize;
    private String nameRegep;

    public final String NAME = "name";

    @Override
    public void trimStringFields(Unit model) {
        if (model.getName() != null) model.setName(model.getName().trim());
    }

    @Override
    public void initialize(UnitConstraint constraintAnnotation) {
        nameMinSize = constraintAnnotation.nameMinSize();
        nameMaxSize = constraintAnnotation.nameMaxSize();
        nameRegep = constraintAnnotation.nameRegexp();
    }

    @Override
    public boolean isValid(Unit unit, ConstraintValidatorContext constraintValidatorContext) {
        int errorCount = 0;
        constraintValidatorContext.disableDefaultConstraintViolation();

        trimStringFields(unit);
        if (unit == null) return true;
        if (!isNameValid(unit, constraintValidatorContext)) ++errorCount;

        return errorCount == 0;
    }

    private boolean isNameValid(Unit unit, ConstraintValidatorContext constraintValidatorContext) {
        String name = unit.getName();
        String msg;
        if (name == null) {
            msg = "{validation.unit.UnitConstraint.message.name.empty}";
            addConstraintViolation(NAME, msg, constraintValidatorContext);
            return false;
        }
        if (name.length() < nameMinSize || name.length() > nameMaxSize || !name.matches(nameRegep)) {
            msg = "{validation.unit.UnitConstraint.message.name.invalid}";
            addConstraintViolation(NAME, msg, constraintValidatorContext);
            return false;
        }
        return true;
    }
}
