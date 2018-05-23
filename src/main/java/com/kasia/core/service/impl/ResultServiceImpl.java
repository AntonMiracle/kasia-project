package com.kasia.core.service.impl;

import com.kasia.core.model.Result;
import com.kasia.core.service.ExceptionService;
import com.kasia.core.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultServiceImpl<T> implements ResultService<T> {
    @Autowired
    private ExceptionService exception;

    @Override
    public Result<T> failed() throws NullPointerException {
        Result<T> result = new Result<>();
        result.setFailed(true);
        result.setResult(null);
        return result;
    }

    @Override
    public Result<T> success(T resultObject) throws NullPointerException {
        exception.NPE(resultObject);

        Result<T> result = new Result<>();
        result.setFailed(false);
        result.setResult(resultObject);
        return result;
    }
}
