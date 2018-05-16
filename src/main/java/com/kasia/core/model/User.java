package com.kasia.core.model;

import java.io.Serializable;

public class User implements Serializable, CoreModel {
    private long id;
    private boolean isNull;
    private boolean isValid;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isNull() {
        return isNull;
    }

    @Override
    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    protected void setId(long id) {
        this.id = id;
    }
}
