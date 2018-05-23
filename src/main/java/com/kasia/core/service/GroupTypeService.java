package com.kasia.core.service;

import com.kasia.core.model.GroupType;
import com.kasia.core.model.Result;

public interface GroupTypeService extends ModelService<GroupType>, ValidatorService<GroupType> {

    Result<Boolean> delete(String name) throws NullPointerException;

    Result<Boolean> isNameExist(String name) throws NullPointerException;

    Result<GroupType> get(String name) throws NullPointerException;
}
