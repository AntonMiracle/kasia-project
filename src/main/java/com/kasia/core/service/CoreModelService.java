package com.kasia.core.service;

import com.kasia.core.model.CoreModel;

import java.util.Set;

public interface CoreModelService<T extends CoreModel> {
    /**
     * Save model if and only if model is unique,
     * otherwise return unique model
     *
     * @param model to save
     * @return {@link T}
     * @see ValidatorService#validation(CoreModel)
     */
    T save(T model);

    /**
     * Update model if and only if model is not unique,
     * otherwise save
     *
     * @param model to save
     * @return {@link T}
     * @see CoreModelService#save(T)
     * @see ValidatorService#validation(CoreModel)
     */
    T update(T model);

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
     * @throws NullPointerException     if {@param id} is null
     * @throws IllegalArgumentException if {@param id} is not exist
     */
    Boolean delete(Long id) throws NullPointerException, IllegalArgumentException;

    /**
     * If and only if no models, return empty Set
     *
     * @return Set
     */
    Set<T> get();

    /**
     * Create new {@link T} where {@link T#isNull()} return true
     *
     * @return new {@link T} where {@link T#isNull()} return true
     */
    T nullModel();

}
