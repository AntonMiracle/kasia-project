package com.kasia.core.service.validator;

import com.kasia.core.model.User;
import com.kasia.core.service.validator.impl.UserValidatorServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserValidatorServiceTest implements HelpTestingValidator {
    private UserValidatorService validatorService;
    private User user;

    @Before
    public void before() {
        validatorService = new UserValidatorServiceImpl();
        user = new User();
    }

    @Test
    public void validatorTrimDetailsFields() {
        user.setLogin(" logisn ");
        user.setPassword("  passss   ");
        validatorService.trimFields(user);
        assertThat(validatorService.isValid(user)).isTrue();
    }

    @Test
    public void detailsHas6Fields() {
        assertThat(validatorService.countFields(user)).isEqualTo(7);
    }

}
