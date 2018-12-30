package com.kasia.model;

import java.util.Set;

public class UserBudget implements Model{
    private User user;
    private Set<Budget> budgets;

    public UserBudget(User user, Set<Budget> budgets) {
        this.user = user;
        this.budgets = budgets;
    }

    public UserBudget() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBudget that = (UserBudget) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return budgets != null ? budgets.equals(that.budgets) : that.budgets == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (budgets != null ? budgets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserBudget{" +
                "user=" + user +
                ", budgets=" + budgets +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(Set<Budget> budgets) {
        this.budgets = budgets;
    }
}
