package com.kasia.core.service.validator;

import com.kasia.core.model.Role;
import com.kasia.core.service.validator.impl.RoleValidatorServiceImpl;
import org.junit.Before;

public class RoleValidatorServiceTest implements TestHelper<Role> {
    private Role role;
    private RoleValidatorService validatorService;

    @Before
    public void before() {
        validatorService = new RoleValidatorServiceImpl();
        role = new Role();
    }



}
