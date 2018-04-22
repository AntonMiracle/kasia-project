package com.kasia.core.service.validator.impl;

import com.kasia.core.model.Role;
import com.kasia.core.service.validator.RoleValidatorService;

public class RoleValidatorServiceImpl implements RoleValidatorService {


    @Override
    public void trimFields(Role role) {
        if(role == null) return;
        if (role.getName() != null) role.setName(role.getName().trim());
    }
}
