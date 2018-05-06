package com.kasia.core.model;

import java.io.Serializable;

public class User implements Serializable, CoreModel {
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
