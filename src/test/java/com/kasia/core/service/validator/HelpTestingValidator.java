package com.kasia.core.service.validator;

public interface HelpTestingValidator<T> {

    default long countConstraintViolation(ValidatorService<T> validatorService,T object, String fieldName) {
        return validatorService.validate(object).stream()
                .filter(error -> error.getPropertyPath().toString().equals(fieldName))
                .count();
    }

    default String getField(Class<T> claz, String name) {
        try {
            return claz.getDeclaredField(name).getName();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Wrong field name", e);
        }
    }
}
