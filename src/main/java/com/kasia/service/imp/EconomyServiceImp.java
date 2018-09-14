package com.kasia.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.repository.EconomyRepository;
import com.kasia.service.EconomyService;

import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EconomyServiceImp implements EconomyService {
    private EconomyRepository repository;

    @Override
    public boolean isValid(Economy economy) throws NullPointerException {
        if (economy == null) throw new NullPointerException();

        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(economy).size() == 0;
        }
    }

    @Override
    public Economy create(String name) throws ValidationException, NullPointerException {
        Economy economy = new Economy();
        economy.setName(name);
        economy.setBudgets(new HashSet<>());
        economy.setStartOn(LocalDateTime.now());

        if (!isValid(economy)) throw new ValidationException();
        return repository.save(economy);
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        Economy economy = getById(id);
        if (economy == null) return true;
        return repository.delete(economy);
    }

    @Override
    public boolean update(Economy economy) throws ValidationException, IllegalArgumentException , NullPointerException{
        if (!isValid(economy)) throw new ValidationException();
        if (economy.getId() <= 0) throw new IllegalArgumentException();
        return repository.update(economy);
    }

    @Override
    public Economy getById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getById(id);
    }

    @Override
    public boolean addBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException {
        if (economy == null || budget == null) throw new NullPointerException();
        if (!isValid(economy) || budget.getId() <= 0) throw new ValidationException();
        if (!economy.getBudgets().add(budget)) return false;
        return update(economy);
    }

    @Override
    public boolean removeBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException {
        if (economy == null || budget == null) throw new NullPointerException();
        if (!isValid(economy) || budget.getId() <= 0) throw new ValidationException();
        if (!economy.getBudgets().remove(budget)) return false;
        return update(economy);
    }

    @Override
    public Map<Currency, BigDecimal> getBalance(Economy economy) throws NullPointerException, ValidationException {
        if (!isValid(economy)) throw new ValidationException();

        Map<Currency, BigDecimal> balances = new HashMap<>();
        for (Budget budget : economy.getBudgets()) {

            BigDecimal oldBalance = balances.get(budget.getCurrency());
            BigDecimal currentBalance = budget.getBalance();
            Currency currency = budget.getCurrency();

            balances.put(currency, oldBalance == null ? currentBalance : oldBalance.add(currentBalance));
        }
        return balances;
    }
}
