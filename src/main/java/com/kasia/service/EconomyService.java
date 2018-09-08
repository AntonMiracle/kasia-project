package com.kasia.service;

import com.kasia.model.Economy;

import javax.validation.ValidationException;

public interface EconomyService extends ValidationService<Economy> {

    Economy create(String name) throws ValidationException, NullPointerException;

    boolean delete(Economy economy) throws NullPointerException, IllegalArgumentException;
}
