package com.kasia.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Budget implements Model, Comparable<Budget> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Embedded
    private Balance balance;
    private LocalDateTime createOn;

    public Budget(String name, Balance balance, LocalDateTime createOn) {
        this.name = name;
        this.balance = balance;
        this.createOn = createOn;
    }

    public Budget() {
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", createOn=" + createOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budget budget = (Budget) o;

        if (id != budget.id) return false;
        if (name != null ? !name.equals(budget.name) : budget.name != null) return false;
        if (balance != null ? !balance.equals(budget.balance) : budget.balance != null) return false;
        return createOn != null ? createOn.equals(budget.createOn) : budget.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
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

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Budget o) {
        return this.name.compareTo(o.name);
    }
}
