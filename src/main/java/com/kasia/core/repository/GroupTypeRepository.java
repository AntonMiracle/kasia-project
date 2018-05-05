package com.kasia.core.repository;

import com.kasia.core.model.GroupType;

public interface GroupTypeRepository extends CoreModelRepository<GroupType> {
    GroupType get(String name);

    Boolean isNameExist(String name);
}
