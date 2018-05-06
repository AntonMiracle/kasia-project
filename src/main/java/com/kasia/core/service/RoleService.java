package com.kasia.core.service;

import com.kasia.core.model.Role;

public interface RoleService extends CoreModelService<Role>, ValidatorService<Role> {
    /**
     * @param name of Role
     * @return true if and only if delete success
     * @throws NullPointerException     if {@param name} is null
     * @throws IllegalArgumentException if {@param name} not exist
     */
    Boolean delete(String name) throws NullPointerException, IllegalArgumentException;

    /**
     * @param name of Role
     * @return true if and only if {@param name} exist
     * @throws NullPointerException if {@param name} is null
     */
    Boolean isNameExist(String name) throws NullPointerException;

    /**
     * @param name of Role
     * @return GroupType with name = {@param name}
     * @throws NullPointerException     if {@param name} is null
     * @throws IllegalArgumentException if {@param name} not exist
     */
    Role get(String name) throws NullPointerException, IllegalArgumentException;

}
