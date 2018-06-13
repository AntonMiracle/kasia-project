package com.kasia.model.result;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private T result;
    private boolean exist;
    private boolean valid;

    public Result() {

    }

    public Result(T result, boolean exist, boolean valid) {
        this.result = result;
        this.exist = exist;
        this.valid = valid;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> result1 = (Result<?>) o;

        if (exist != result1.exist) return false;
        if (valid != result1.valid) return false;
        return result != null ? result.equals(result1.result) : result1.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (exist ? 1 : 0);
        result1 = 31 * result1 + (valid ? 1 : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", exist=" + exist +
                ", valid=" + valid +
                '}';
    }
}
