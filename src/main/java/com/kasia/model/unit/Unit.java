package com.kasia.model.unit;

import com.kasia.model.Model;

import java.io.Serializable;

public class Unit extends Model implements Serializable {
    private String name;

    public Unit(String name) {
        this.name = name;
    }

    public Unit() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
