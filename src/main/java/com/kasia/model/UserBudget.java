package com.kasia.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserBudget implements Model {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Budget> budgets = new HashSet<>();

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

        if (id != that.id) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return budgets != null ? budgets.equals(that.budgets) : that.budgets == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (budgets != null ? budgets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserBudget{" +
                "id=" + id +
                ", user=" + user +
                ", budgets=" + budgets +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
