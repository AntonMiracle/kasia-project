package com.kasia.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class UserConnectBudget implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
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

        if (id != that.id) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return connectBudgets != null ? connectBudgets.equals(that.connectBudgets) : that.connectBudgets == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (connectBudgets != null ? connectBudgets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserConnectBudget{" +
                "id=" + id +
                ", user=" + user +
                ", connectBudgets=" + connectBudgets +
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

    public Set<Budget> getConnectBudgets() {
        return connectBudgets;
    }

    public void setConnectBudgets(Set<Budget> connectBudgets) {
        this.connectBudgets = connectBudgets;
    }
}
