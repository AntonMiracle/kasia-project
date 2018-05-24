package com.kasia.core.service.impl;

import com.kasia.core.model.GroupType;
import com.kasia.core.model.Result;
import com.kasia.core.repository.GroupTypeRepository;
import com.kasia.core.service.ExceptionService;
import com.kasia.core.service.GroupTypeService;
import com.kasia.core.service.ResultService;
import com.kasia.core.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Transactional
public class GroupTypeServiceImpl implements GroupTypeService {
    @Autowired
    private GroupTypeRepository groupTypeRepository;
    @Autowired
    private ValidatorService<GroupType> validator;
    @Autowired
    private GroupTypeService service;
    @Autowired
    private ResultService<GroupType> resultGroupType;
    @Autowired
    private ResultService<Boolean> resultBoolean;
    @Autowired
    private ResultService<Set<GroupType>> resultSetGroupType;
    @Autowired
    private ExceptionService exception;

    //VALIDATOR SERVICE =========================================================================
    @Override
    public Result<GroupType> eliminateNull(GroupType model) throws NullPointerException {
        exception.NPE(model);

        if (model.getName() == null) model.setName("");

        return resultGroupType.calculationSuccess(model);
    }

    @Override
    public Result<GroupType> validation(GroupType model) throws NullPointerException {
        exception.NPE(model);

        int errors = validator.getValidator().validate(model).size();
        if (errors != 0) return resultGroupType.calculationFailed();

        return resultGroupType.calculationSuccess(model);
    }

    //GROUP TYPE SERVICE ======================================================================
    @Override
    public Result<GroupType> save(GroupType model) throws NullPointerException {
        exception.NPE(model);

        Result<Boolean> result = service.isNameExist(model.getName());
        if (result.isCalculationFailed() || result.getResult()) return resultGroupType.calculationFailed();

        model = groupTypeRepository.saveOrUpdate(model);
        if (model == null) return resultGroupType.calculationFailed();

        return resultGroupType.calculationSuccess(model);
    }

    @Override
    public Result<GroupType> update(GroupType model) throws NullPointerException {
        exception.NPE(model);

        Result<Boolean> result = service.isNameExist(model.getName());
        if (result.isCalculationFailed() || !result.getResult()) return resultGroupType.calculationFailed();

        model = groupTypeRepository.saveOrUpdate(model);
        if (model == null) resultGroupType.calculationFailed();

        return resultGroupType.calculationSuccess(model);
    }

    @Override
    public Result<GroupType> get(Long id) throws NullPointerException {
        exception.NPE(id);

        GroupType model = groupTypeRepository.get(id);
        if (model == null) return resultGroupType.calculationFailed();

        return resultGroupType.calculationSuccess(model);
    }

    @Override
    public Result<Boolean> delete(Long id) throws NullPointerException {
        exception.NPE(id);

        if (service.get(id).isCalculationFailed()) return resultBoolean.calculationFailed();

        if (!groupTypeRepository.delete(id)) return resultBoolean.calculationFailed();

        return resultBoolean.calculationSuccess(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> delete(String name) throws NullPointerException {
        exception.NPE(name);

        Result<GroupType> result = service.get(name);
        if (result.isCalculationFailed()) return resultBoolean.calculationFailed();

        return service.delete(result.getResult().getId());
    }

    @Override
    public Result<Set<GroupType>> get() {
        Set<GroupType> all = groupTypeRepository.get();

        if (all == null) return resultSetGroupType.calculationFailed();

        return resultSetGroupType.calculationSuccess(all);
    }

    @Override
    public Result<Boolean> isNameExist(String name) throws NullPointerException {
        exception.NPE(name);

        if (!groupTypeRepository.isNameExist(name)) return resultBoolean.calculationFailed();

        return resultBoolean.calculationSuccess(Boolean.TRUE);
    }

    @Override
    public Result<GroupType> get(String name) throws NullPointerException {
        exception.NPE(name);

        GroupType model = groupTypeRepository.get(name);
        if (model == null) return resultGroupType.calculationFailed();

        return resultGroupType.calculationSuccess(model);
    }

}
