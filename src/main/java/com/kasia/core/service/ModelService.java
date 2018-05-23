package com.kasia.core.service;

import com.kasia.core.model.Model;
import com.kasia.core.model.Result;

import java.util.Set;

public interface ModelService<T extends Model> {

    Result<T> save(T model) throws NullPointerException;

    Result<T> update(T model) throws NullPointerException;

    Result<T> get(Long id) throws NullPointerException;

    Result<Boolean> delete(Long id) throws NullPointerException;

    Result<Set<T>> get();

}
