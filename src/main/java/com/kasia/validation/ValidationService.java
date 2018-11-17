package com.kasia.validation;

import com.kasia.exception.RegexNotExistRunTimeException;
import com.kasia.model.Model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.regex.Pattern;

public class ValidationService {
    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
    public static final String NAME = "^[A-Za-z0-9+_.-]{3,}$";

    public boolean isMatches(String value, String regex) throws RegexNotExistRunTimeException {
        if (!isExist(regex)) throw new RegexNotExistRunTimeException();
        return Pattern.compile(regex).matcher(value).matches();
    }

    private boolean isExist(String regex) {
        switch (regex) {
            case EMAIL:
                return true;
            case PASSWORD:
                return true;
            case NAME:
                return true;
            default:
                return false;
        }
    }

    public boolean isValid(Model model) {
        if (model == null) return true;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> violations = validator.validate(model);
        return violations.size() == 0;
    }
}
