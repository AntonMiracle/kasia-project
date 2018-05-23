package com.kasia.core.model;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private boolean failed;
    private T result;

    public Result() {

    }

    public Result(T result) {
        setResult(result);
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
