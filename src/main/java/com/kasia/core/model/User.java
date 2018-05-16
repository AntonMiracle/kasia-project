package com.kasia.core.model;

import java.io.Serializable;

public class User implements Serializable, CoreModel {
    private long id;
    private boolean isNull;

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

    protected void setId(long id) {
        this.id = id;
    }
}
