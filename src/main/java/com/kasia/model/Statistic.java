package com.kasia.model;

import java.util.Set;

public class Statistic implements Model{
    private Set<Operation> operations;

    public Statistic(Set<Operation> operations) {
        this.operations = operations;
    }

    public Statistic() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistic statistic = (Statistic) o;

        return operations != null ? operations.equals(statistic.operations) : statistic.operations == null;
    }

    @Override
    public int hashCode() {
        return operations != null ? operations.hashCode() : 0;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
