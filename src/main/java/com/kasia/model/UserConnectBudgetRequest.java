package com.kasia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserConnectBudgetRequest implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long budgetId;
    private long fromUserId;
    private long toUserId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserConnectBudgetRequest that = (UserConnectBudgetRequest) o;

        if (id != that.id) return false;
        if (budgetId != that.budgetId) return false;
        if (fromUserId != that.fromUserId) return false;
        return toUserId == that.toUserId;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (budgetId ^ (budgetId >>> 32));
        result = 31 * result + (int) (fromUserId ^ (fromUserId >>> 32));
        result = 31 * result + (int) (toUserId ^ (toUserId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "UserConnectBudgetRequest{" +
                "id=" + id +
                ", budgetId=" + budgetId +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                '}';
    }
}
