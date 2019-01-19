package com.kasia.model.service;

import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.ElementProvider;

import javax.validation.ValidationException;
import java.util.Set;

public interface ElementProviderService {
    ElementProvider save(ElementProvider model) throws ValidationException;

    boolean delete(ElementProvider model) throws IdInvalidRuntimeException, ValidationException;

    ElementProvider findById(long id) throws IdInvalidRuntimeException;

    Set<ElementProvider> findAll();

    ElementProvider create(String name, String description) throws ValidationException;
}
