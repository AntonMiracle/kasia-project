package com.kasia.model.service;

import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.Element;
import com.kasia.model.ElementType;
import com.kasia.model.Price;

import javax.validation.ValidationException;
import java.util.Set;

public interface ElementService {
    Element save(Element model) throws ValidationException;

    boolean delete(Element model) throws IdInvalidRuntimeException, ValidationException;

    Element findById(long id) throws IdInvalidRuntimeException;

    Set<Element> findAll();

    Element create(String name, ElementType type, Price defaultPrice) throws ValidationException;
}
