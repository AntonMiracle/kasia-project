package com.kasia.core.service;

import com.kasia.core.model.GroupType;

public interface GroupTypeService extends CoreModelService<GroupType>, ValidatorService<GroupType> {
    /**
     * @param name of GroupType
     * @return true if and only if delete success
     * @throws NullPointerException     if {@param name} is null
     * @throws IllegalArgumentException if {@param name} not exist
     */
    Boolean delete(String name) throws NullPointerException, IllegalArgumentException;

    /**
     * @param name of GroupType
     * @return true if and only if {@param name} exist
     * @throws NullPointerException if {@param name} is null
     */
    Boolean isNameExist(String name) throws NullPointerException;

    /**
     * @param name of GroupType
     * @return GroupType with name = {@param name}
     * @throws NullPointerException     if {@param name} is null
     * @throws IllegalArgumentException if {@param name} not exist
     */
    GroupType get(String name) throws NullPointerException, IllegalArgumentException;
}
