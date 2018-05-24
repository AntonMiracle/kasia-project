package com.kasia.core.model;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private boolean calculationFailed;
    private T result;

    public Result() {

    }

    public Result(T result) {
        setResult(result);
    }

    public boolean isCalculationFailed() {
        return calculationFailed;
    }

    public void setCalculationFailed(boolean calculationFailed) {
        this.calculationFailed = calculationFailed;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
