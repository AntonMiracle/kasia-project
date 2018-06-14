package com.kasia.service;

import com.kasia.model.user.User;
import com.kasia.validation.ValidationService;
import com.kasia.validation.imp.ValidationServiceImp;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationServiceTest {
    private ValidationService service;
    private Validator validator;

    @Before
    public void before() {
        service = new ValidationServiceImp();
        validator = service.getValidator();
    }

    // USER ================================================
    @Test
    public void test() {
        User user = new User();
        user.setUsername("Username");
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        errors.forEach(el -> System.out.println(el.getPropertyPath().toString() + "   " + el.getMessage()));
    }

}