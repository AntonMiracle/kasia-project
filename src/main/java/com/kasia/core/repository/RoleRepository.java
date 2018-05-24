package com.kasia.core.repository;

import com.kasia.core.model.Role;

public interface RoleRepository extends ModelRepository<Role> {
    Role get(String name);

    boolean isNameExist(String name);

}
