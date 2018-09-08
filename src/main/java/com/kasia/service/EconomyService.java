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

    boolean update(Economy economy) throws ValidationException, IllegalArgumentException;

    Economy getById(long id) throws IllegalArgumentException;

    boolean addBudget(Budget budget) throws NullPointerException, IllegalArgumentException;

    boolean removeBudget(Budget budget) throws NullPointerException, IllegalArgumentException;

    Map<Currency, BigDecimal> getBalance(Economy economy) throws NullPointerException;



}
