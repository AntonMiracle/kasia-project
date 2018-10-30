package com.kasia.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.repository.EconomyRepository;
import com.kasia.service.EconomyService;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class EconomyServiceImp implements EconomyService {
    @EJB
    private EconomyRepository economyRepository;

    @Override
    public boolean isValid(Economy economy) throws NullPointerException {
        if (economy == null) throw new NullPointerException();

        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(economy).size() == 0;
        }
    }

    @Override
    public Economy create(String name) throws ValidationException, NullPointerException {
        Economy economy = new Economy(name, LocalDateTime.now().withNano(0));
        economy.setBudgets(new HashSet<>());
        if (!isValid(economy)) throw new ValidationException();
        economyRepository.save(economy);
        return economyRepository.getById(economy.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        Economy economy = economyRepository.getById(id);
        if (economy == null) return true;
        return economyRepository.delete(economy);
    }

    @Override
    public Economy update(Economy economy) throws ValidationException, IllegalArgumentException, NullPointerException {
        if (!isValid(economy)) throw new ValidationException();
        if (economy.getId() == 0) throw new IllegalArgumentException();
        economyRepository.save(economy);
        return economyRepository.getById(economy.getId());
    }

    @Override
    public Economy getEconomyById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return economyRepository.getById(id);
    }

    @Override
    public Economy addBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException {
        if (economy == null || budget == null) throw new NullPointerException();
        if (!isValid(economy)) throw new ValidationException();

        Set<Budget> newBudgets = new HashSet<>(economy.getBudgets());
        if (!newBudgets.add(budget)) return null;

        economy.setBudgets(newBudgets);
        economyRepository.save(economy);
        return economyRepository.getById(economy.getId());
    }

    @Override
    public Economy removeBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException {
        if (economy == null || budget == null) throw new NullPointerException();
        if (!isValid(economy)) throw new ValidationException();

        Set<Budget> newBudgets = new HashSet<>(economy.getBudgets());
        if (!newBudgets.remove(budget)) return null;

        economy.setBudgets(newBudgets);

        economyRepository.save(economy);
        return economyRepository.getById(economy.getId());
    }

    @Override
    public Map<Currency, BigDecimal> getBalance(Economy economy) throws NullPointerException, ValidationException {
        if (!isValid(economy)) throw new ValidationException();

        Map<Currency, BigDecimal> balances = new HashMap<>();
        for (Budget budget : economy.getBudgets()) {

            Currency currency = budget.getCurrency();
            BigDecimal oldBalance = balances.get(currency);
            BigDecimal currentBalance = budget.getBalance();

            balances.put(currency, oldBalance == null ? currentBalance : oldBalance.add(currentBalance));
        }
        return balances;
    }

    @Override
    public Set<Economy> getAll() {
        return economyRepository.getAll();
    }
}
