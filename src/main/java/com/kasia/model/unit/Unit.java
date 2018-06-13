package com.kasia.model.unit;

import com.kasia.model.Model;

import java.io.Serializable;

public class Unit extends Model implements Serializable {
    private int amount;
    private String name;

    public Unit(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public Unit() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Unit unit = (Unit) o;

        if (amount != unit.amount) return false;
        return name != null ? name.equals(unit.name) : unit.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + amount;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "amount=" + amount +
                ", name='" + name + '\'' +
                '}';
    }
}
