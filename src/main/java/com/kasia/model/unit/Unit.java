package com.kasia.model.unit;

import com.kasia.model.Model;
import com.kasia.model.user.User;

import java.io.Serializable;

public class Unit extends Model implements Serializable {
    private User maker;
    private int amount;
    private String name;

    public Unit(String name, int amount, User maker) {
        this.name = name;
        this.amount = amount;
        this.maker = maker;
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

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public User getMaker() {
        return maker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Unit unit = (Unit) o;

        if (amount != unit.amount) return false;
        if (maker != null ? !maker.equals(unit.maker) : unit.maker != null) return false;
        return name != null ? name.equals(unit.name) : unit.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (maker != null ? maker.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "maker=" + maker +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                '}';
    }
}
