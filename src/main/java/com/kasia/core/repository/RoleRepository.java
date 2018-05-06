package com.kasia.core.repository;

import com.kasia.core.model.Role;

public interface RoleRepository extends CoreModelRepository<Role> {
    Role get(String name);

    Boolean isNameExist(String name);

}
