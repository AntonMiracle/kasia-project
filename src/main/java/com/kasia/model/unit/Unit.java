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

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Unit unit = (Unit) o;

        return name != null ? name.equals(unit.name) : unit.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
