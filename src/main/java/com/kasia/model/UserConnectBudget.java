package com.kasia.model;

import java.util.Set;

public class UserConnectBudget implements Model{
    private User user;
    private Set<Budget> connectBudgets;

    public UserConnectBudget(User user, Set<Budget> connectBudgets) {
        this.user = user;
        this.connectBudgets = connectBudgets;
    }

    public UserConnectBudget() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserConnectBudget that = (UserConnectBudget) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return connectBudgets != null ? connectBudgets.equals(that.connectBudgets) : that.connectBudgets == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (connectBudgets != null ? connectBudgets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserConnectBudget{" +
                "user=" + user +
                ", connectBudgets=" + connectBudgets +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Budget> getConnectBudgets() {
        return connectBudgets;
    }

    public void setConnectBudgets(Set<Budget> connectBudgets) {
        this.connectBudgets = connectBudgets;
    }
}
