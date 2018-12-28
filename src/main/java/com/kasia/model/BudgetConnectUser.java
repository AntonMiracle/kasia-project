package com.kasia.model;

import java.util.Set;

public class BudgetConnectUser implements Model{
    private Budget budget;
    private Set<User> connectUsers;

    public BudgetConnectUser() {
    }

    public BudgetConnectUser(Budget budget, Set<User> connectUsers) {
        this.budget = budget;
        this.connectUsers = connectUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetConnectUser that = (BudgetConnectUser) o;

        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        return connectUsers != null ? connectUsers.equals(that.connectUsers) : that.connectUsers == null;
    }

    @Override
    public int hashCode() {
        int result = budget != null ? budget.hashCode() : 0;
        result = 31 * result + (connectUsers != null ? connectUsers.hashCode() : 0);
        return result;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Set<User> getConnectUsers() {
        return connectUsers;
    }

    public void setConnectUsers(Set<User> connectUsers) {
        this.connectUsers = connectUsers;
    }
}
