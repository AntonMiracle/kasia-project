package com.kasia.core.service;

import com.kasia.core.model.Result;

public interface ResultService<T> {

    Result<T> failed();

    Result<T> success(T resultObject) throws NullPointerException;
}
