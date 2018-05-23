package com.kasia.core.model;

import java.io.Serializable;

public class User implements Serializable, Model {
    private long id;
    private boolean actionSuccess;

    @Override
    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

}
