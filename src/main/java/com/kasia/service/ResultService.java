package com.kasia.service;

import com.kasia.model.result.Result;

public interface ResultService<T> {

    default Result<T> success(T resultValue) {
        Result<T> result = new Result<>();
        result.setValid(true);
        result.setExist(true);
        result.setResult(resultValue);
        return result;
    }

    default Result<T> success() {
        Result<T> result = new Result<>();
        result.setValid(true);
        return result;
    }

    default Result<T> failed() {
        Result<T> result = new Result<>();
        return result;
    }
}
