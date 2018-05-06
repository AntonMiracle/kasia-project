package com.kasia.core.service;

import javax.validation.ConstraintViolationException;
import java.util.Set;

public interface CoreModelService<T> {
    /**
     * @param model
     * @return saved or updated model
     * @throws ConstraintViolationException if validation false
     * @throws NullPointerException         if {@param model} is null
     * @throws IllegalArgumentException     if at least one {@param model} field illegal
     */
    T saveOrUpdate(T model) throws ConstraintViolationException, NullPointerException, IllegalArgumentException;

    /**
     * @param id
     * @return model with {@param id}
     * @throws NullPointerException     if {@param id} null
     * @throws IllegalArgumentException if {@param id} not exist
     */
    T get(Long id) throws NullPointerException, IllegalArgumentException;

    /**
     * @param id
     * @return true if and only if delete success
     * @throws NullPointerException if {@param id} is null
     * @throws IllegalArgumentException if {@param id} is not exist
     */
    Boolean delete(Long id) throws NullPointerException, IllegalArgumentException;

    /**
     * @return all models
     */
    Set<T> get();
}
