package com.kasia.core.service.impl;

import com.kasia.core.model.GroupType;
import com.kasia.core.repository.GroupTypeRepository;
import com.kasia.core.service.GroupTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class GroupTypeServiceImpl implements GroupTypeService {
    @Autowired
    private GroupTypeRepository repository;

    @Override
    public void setDefaultValuesIfNull(GroupType model) {
        if (model.getName() == null) {
            model.setName("");
        }
    }

    @Override
    public GroupType saveOrUpdate(GroupType model) throws ConstraintViolationException, NullPointerException, IllegalArgumentException {
        if (model == null) {
            throw new NullPointerException();
        }

        setDefaultValuesIfNull(model);

        if (repository.isNameExist(model.getName())) {
            throw new IllegalArgumentException();
        }

        return repository.saveOrUpdate(model);
    }

    @Override
    public GroupType get(Long id) throws NullPointerException, IllegalArgumentException {
        if (id == null) {
            throw new NullPointerException();
        }

        GroupType groupType = repository.get(id);

        if (groupType == null) {
            throw new IllegalArgumentException();
        }

        return groupType;
    }

    @Override
    public Boolean delete(Long id) throws NullPointerException, IllegalArgumentException {
        return repository.delete(id);
    }

    @Override
    public Set<GroupType> get() {
        Set<GroupType> all = repository.get();
        return all != null ? all : new HashSet<>();
    }

    @Override
    public Boolean delete(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException();
        }

        GroupType groupType = repository.get(name);

        if (groupType == null) {
            throw new IllegalArgumentException();
        }

        return delete(groupType.getId());
    }

    @Override
    public Boolean isNameExist(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }
        return repository.isNameExist(name);
    }

    @Override
    public GroupType get(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException();
        }

        GroupType groupType = repository.get(name);

        if (groupType == null) {
            throw new IllegalArgumentException();
        }

        return groupType;
    }
}
