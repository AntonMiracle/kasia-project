package com.kasia.model;

import java.time.LocalDateTime;

public class Budget implements Model{
    private String name;// unique in user scope
    private Price balance;
    private LocalDateTime createOn;

    public Budget(String name, Price balance, LocalDateTime createOn) {
        this.name = name;
        this.balance = balance;
        this.createOn = createOn;
    }

    public Budget() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budget budget = (Budget) o;

        if (name != null ? !name.equals(budget.name) : budget.name != null) return false;
        if (balance != null ? !balance.equals(budget.balance) : budget.balance != null) return false;
        return createOn != null ? createOn.equals(budget.createOn) : budget.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getBalance() {
        return balance;
    }

    public void setBalance(Price balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }
}
