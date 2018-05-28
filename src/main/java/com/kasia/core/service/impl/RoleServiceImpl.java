package com.kasia.core.service.impl;

import com.kasia.core.model.Result;
import com.kasia.core.model.Role;
import com.kasia.core.repository.RoleRepository;
import com.kasia.core.service.ExceptionService;
import com.kasia.core.service.ResultService;
import com.kasia.core.service.RoleService;
import com.kasia.core.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ValidatorService<Role> validator;
    @Autowired
    private RoleService service;
    @Autowired
    private ResultService<Role> resultRole;
    @Autowired
    private ResultService<Boolean> resultBoolean;
    @Autowired
    private ResultService<Set<Role>> resultSetRole;
    @Autowired
    private ExceptionService exception;

    //VALIDATOR SERVICE =========================================================================
    @Override
    public Result<Role> eliminateNull(Role model) {
        exception.NPE(model);

        if (model.getName() == null) model.setName("");

        return resultRole.calculationSuccess(model);
    }

    @Override
    public Result<Boolean> validation(Role model) throws NullPointerException {
        exception.NPE(model);

        int errors = validator.getValidator().validate(model).size();

        return resultBoolean.calculationSuccess(errors == 0);
    }

    //ROLE SERVICE ======================================================================
    @Override
    public Result<Boolean> delete(String name) throws NullPointerException {
        exception.NPE(name);

        Result<Role> result = service.get(name);
        if (result.isCalculationFailed()) return resultBoolean.calculationFailed();

        return service.delete(result.getResult().getId());
    }

    @Override
    public Result<Boolean> isNameExist(String name) throws NullPointerException {
        exception.NPE(name);

        boolean result = roleRepository.isNameExist(name);
        return resultBoolean.calculationSuccess(result);
    }

    @Override
    public Result<Role> get(String name) throws NullPointerException {
        exception.NPE(name);

        Role model = roleRepository.get(name);
        if (model == null) return resultRole.calculationFailed();

        return resultRole.calculationSuccess(model);
    }

    @Override
    public Result<Role> save(Role model) throws NullPointerException {
        exception.NPE(model);

        Result<Boolean> result = service.isNameExist(model.getName());
        if (result.isCalculationFailed() || result.getResult()) return resultRole.calculationFailed();

        model = roleRepository.saveOrUpdate(model);
        if (model == null) return resultRole.calculationFailed();

        return resultRole.calculationSuccess(model);
    }

    @Override
    public Result<Role> update(Role model) throws NullPointerException {
        exception.NPE(model);

        if (model.getId() <= 0) return resultRole.calculationFailed();

        model = roleRepository.saveOrUpdate(model);
        if (model == null) resultRole.calculationFailed();

        return resultRole.calculationSuccess(model);
    }

    @Override
    public Result<Role> get(Long id) throws NullPointerException {
        exception.NPE(id);

        Role model = roleRepository.get(id);
        if (model == null) return resultRole.calculationFailed();

        return resultRole.calculationSuccess(model);
    }

    @Override
    public Result<Boolean> delete(Long id) throws NullPointerException {
        exception.NPE(id);

        if (service.get(id).isCalculationFailed()) return resultBoolean.calculationSuccess(Boolean.FALSE);

        return resultBoolean.calculationSuccess(roleRepository.delete(id));
    }

    @Override
    public Result<Set<Role>> get() {
        Set<Role> all = roleRepository.get();

        if (all == null) return resultSetRole.calculationFailed();

        return resultSetRole.calculationSuccess(all);
    }

}
