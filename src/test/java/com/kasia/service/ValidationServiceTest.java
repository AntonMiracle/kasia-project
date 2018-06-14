package com.kasia.service;

import com.kasia.model.user.User;
import com.kasia.validation.ValidationService;
import com.kasia.validation.imp.ValidationServiceImp;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
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
        service = new ValidationServiceImp<User>();
        User user = new User();
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        Map<String, String> map = service.mapErrorFieldsWithMsg(errors);
        for (String key : map.keySet()){
            System.out.println("FIELDS : " + key + " | MSG : " + map.get(key));
        }
    }

}