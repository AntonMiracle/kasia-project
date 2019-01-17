package com.kasia.model.validation.imp;

import com.kasia.model.User;
import com.kasia.model.validation.FieldName;
import com.kasia.model.validation.UserValidationService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class UserValidationServiceImp implements UserValidationService {

    @Override
    public boolean isPasswordValid(String password) {
        User user = new User();
        user.setPassword(password);

        Validator validator = getValidator();
        for (ConstraintViolation<User> violation : validator.validate(user)) {
            if (violation.getPropertyPath().toString().equals(FieldName.USER_PASSWORD.getName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmailValid(String email) {
        User user = new User();
        user.setEmail(email);

        Validator validator = getValidator();
        for (ConstraintViolation<User> violation : validator.validate(user)) {
            if (violation.getPropertyPath().toString().equals(FieldName.USER_EMAIL.getName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isNameValid(String name) {
        User user = new User();
        user.setName(name);

        Validator validator = getValidator();
        for (ConstraintViolation<User> violation : validator.validate(user)) {
            if (violation.getPropertyPath().toString().equals(FieldName.USER_NAME.getName())) {
                return false;
            }
        }
        return true;
    }
}
