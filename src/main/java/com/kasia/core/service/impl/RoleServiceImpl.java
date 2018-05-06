package com.kasia.core.service.impl;

import com.kasia.core.model.Role;
import com.kasia.core.repository.RoleRepository;
import com.kasia.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public Boolean delete(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException();
        }

        Role role = repository.get(name);

        if (role == null) {
            throw new IllegalArgumentException();
        }

        return repository.delete(role.getId());
    }

    @Override
    public Boolean isNameExist(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }
        return repository.isNameExist(name);
    }

    @Override
    public Role get(String name) throws NullPointerException, IllegalArgumentException {
        if (name == null) {
            throw new NullPointerException();
        }

        Role role = repository.get(name);

        if (role == null) {
            throw new IllegalArgumentException();
        }

        return role;
    }

    @Override
    public Role saveOrUpdate(Role model) throws ConstraintViolationException, NullPointerException, IllegalArgumentException {
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
    public Role get(Long id) throws NullPointerException, IllegalArgumentException {
        if (id == null) {
            throw new NullPointerException();
        }

        Role role = repository.get(id);

        if (role == null) {
            throw new IllegalArgumentException();
        }

        return role;
    }

    @Override
    public Boolean delete(Long id) throws NullPointerException, IllegalArgumentException {
        return repository.delete(id);
    }

    @Override
    public Set<Role> get() {
        Set<Role> all = repository.get();
        return all != null ? all : new HashSet<>();
    }

    @Override
    public void setDefaultValuesIfNull(Role model) {
        if (model.getName() == null) {
            model.setName("");
        }
    }
}
