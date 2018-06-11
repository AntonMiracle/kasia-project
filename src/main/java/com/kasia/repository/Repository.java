package com.kasia.repository;

import com.kasia.model.Model;
import com.kasia.model.result.Result;

import java.util.Set;

public interface Repository<T extends Model> {

    Result<T> add(T model);

    Result<Boolean> remove(long id);

    Result<T> update(T model);

    Result<Set<T>> getAll();

    Result<T> getById(long id);
}
