package com.kasia.service;

import javax.validation.Validation;
import javax.validation.Validator;

public interface ValidationService {

    default Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }


}
