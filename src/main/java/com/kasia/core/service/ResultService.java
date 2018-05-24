package com.kasia.core.service;

import com.kasia.core.model.Result;

public interface ResultService<T> {

    Result<T> calculationFailed();

    Result<T> calculationSuccess(T resultObject) throws NullPointerException;
}
