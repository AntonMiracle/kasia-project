package com.kasia.model.result;

public class Result<T> {
    private T result;
    private boolean exist;
    private boolean valid;

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }


    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public boolean isExist() {
        return exist;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}
