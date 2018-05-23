package com.kasia.core.service;

import com.kasia.core.model.Result;
import com.kasia.core.model.Role;

public interface RoleService extends ModelService<Role>, ValidatorService<Role> {

    Result<Boolean> delete(String name) throws NullPointerException;

    Result<Boolean> isNameExist(String name) throws NullPointerException;

    Result<Role> get(String name) throws NullPointerException;

}
