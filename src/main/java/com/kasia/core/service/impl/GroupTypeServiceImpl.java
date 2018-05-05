package com.kasia.core.service.impl;

import com.kasia.core.model.GroupType;
import com.kasia.core.service.GroupTypeService;
import org.springframework.stereotype.Component;

@Component
public class GroupTypeServiceImpl implements GroupTypeService {
    @Override
    public void setDefaultValuesIfNull(GroupType model) {
        if (model.getName() == null) model.setName("");
    }
}
