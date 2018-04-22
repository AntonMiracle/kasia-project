package com.kasia.core.service.validator.impl;

import com.kasia.core.model.Role;
import com.kasia.core.service.validator.RoleValidatorService;

public class RoleValidatorServiceImpl implements RoleValidatorService {


    @Override
    public void trimFields(Role object) {
        if (object.getName() != null) object.setName(object.getName().trim());
    }
}
