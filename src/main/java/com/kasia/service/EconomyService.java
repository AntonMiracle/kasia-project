package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.Economy;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

public interface EconomyService extends ValidationService<Economy> {

    Economy create(String name) throws ValidationException, NullPointerException;

    boolean delete(long id) throws IllegalArgumentException;

    boolean update(Economy economy) throws ValidationException, NullPointerException, IllegalArgumentException;

    Economy getEconomyById(long id) throws IllegalArgumentException;

    boolean addBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException;

    boolean removeBudget(Economy economy, Budget budget) throws NullPointerException, ValidationException;

    Map<Currency, BigDecimal> getBalance(Economy economy) throws NullPointerException, ValidationException;


}
