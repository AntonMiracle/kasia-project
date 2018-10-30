package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.Economy;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Set;

public interface EconomyService extends ValidationService<Economy>, Service {

    Economy create(String name) throws ValidationException, NullPointerException;

    boolean delete(long id) throws IllegalArgumentException;

    Economy update(Economy economy) throws ValidationException, NullPointerException, IllegalArgumentException;

    Economy getEconomyById(long id) throws IllegalArgumentException;

    Economy addBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException;

    Economy removeBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException;

    Map<Currency, BigDecimal> getBalance(Economy economy) throws NullPointerException, ValidationException;

    Set<Economy> getAll();
}
