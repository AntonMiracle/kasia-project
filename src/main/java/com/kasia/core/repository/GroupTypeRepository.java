package com.kasia.core.repository;

import com.kasia.core.model.GroupType;

public interface GroupTypeRepository extends ModelRepository<GroupType> {
    GroupType get(String name);

    boolean isNameExist(String name);
}
