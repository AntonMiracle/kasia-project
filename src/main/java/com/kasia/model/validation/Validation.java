package com.kasia.model.validation;

import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.Model;

import javax.validation.*;
import java.util.Set;

public interface Validation<T extends Model> {
    String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    String PATTERN_NAME = "^(\\S+.*[\\S\\d]){2,16}$";
    String PATTERN_EMAIL = "^\\S+@\\S+$";
    String PATTERN_DESCRIPTION = "^(?=\\S.*\\S).{2,250}$";

    default boolean isValid(T model) {
        if (model == null) return false;

        ValidatorFactory factory = javax.validation.Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }

    default Validator getValidator() {
        return javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
    }

    default void verifyValidation(T model) throws ValidationException {
        if (!isValid(model)) throw new ValidationException();
    }

    default void verifyPositiveId(long id) throws IdInvalidRuntimeException {
        if (id <= 0) throw new IdInvalidRuntimeException();
    }

}
