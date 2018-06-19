package com.kasia.validator.price;

import com.kasia.model.price.Price;
import com.kasia.validator.ConstraintValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceConstraintValidation implements ConstraintValidator<PriceConstraint, Price>, ConstraintValidation<Price> {
    private long max;

    public final String AMOUNT = "amount";

    @Override
    public void initialize(PriceConstraint constraintAnnotation) {
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Price price, ConstraintValidatorContext constraintValidatorContext) {
        int errorCount = 0;
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (price == null) return true;
        if (!isAmountValid(price, constraintValidatorContext)) ++errorCount;

        return errorCount == 0;
    }

    private boolean isAmountValid(Price price, ConstraintValidatorContext constraintValidatorContext) {
        String msg;
        if (price.getAmount() < 0) {
            msg = "{validation.price.PriceConstraint.message.amount.invalid}";
            addConstraintViolation(AMOUNT, msg, constraintValidatorContext);
            return false;
        }
        return true;
    }
}
