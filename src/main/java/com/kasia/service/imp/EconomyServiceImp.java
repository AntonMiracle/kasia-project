package com.kasia.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.service.EconomyService;

import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

public class EconomyServiceImp implements EconomyService {
    @Override
    public boolean isValid(Economy model) throws NullPointerException {
        if (model == null) throw new NullPointerException();

        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }

    @Override
    public Economy create(String name) throws ValidationException, NullPointerException {
        return null;
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean update(Economy economy) throws ValidationException, IllegalArgumentException {
        return false;
    }

    @Override
    public Economy getById(long id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean addBudget(Budget budget) throws NullPointerException, IllegalArgumentException {
        return false;
    }

    @Override
    public boolean removeBudget(Budget budget) throws NullPointerException, IllegalArgumentException {
        return false;
    }

    @Override
    public Map<Currency, BigDecimal> getBalance(Economy economy) throws NullPointerException {
        return null;
    }
}
