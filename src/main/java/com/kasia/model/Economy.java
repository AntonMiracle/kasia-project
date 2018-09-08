package com.kasia.model;

import java.time.LocalDateTime;
import java.util.Set;

public class Economy implements Model {
    private long id;
    private String name;
    private Set<Budget> budgets;
    private LocalDateTime startOn;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(Set<Budget> budgets) {
        this.budgets = budgets;
    }

    public LocalDateTime getStartOn() {
        return startOn;
    }

    public void setStartOn(LocalDateTime startOn) {
        this.startOn = startOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Economy economy = (Economy) o;

        if (id != economy.id) return false;
        if (name != null ? !name.equals(economy.name) : economy.name != null) return false;
        if (budgets != null ? !budgets.equals(economy.budgets) : economy.budgets != null) return false;
        return startOn != null ? startOn.equals(economy.startOn) : economy.startOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (budgets != null ? budgets.hashCode() : 0);
        result = 31 * result + (startOn != null ? startOn.hashCode() : 0);
        return result;
    }
}
