package com.kasia.model;

import java.util.Set;

public class UserOperation implements Model{
    private User user;
    private Set<Operation> operations;

    public UserOperation() {
    }

    public UserOperation(User user, Set<Operation> operations) {
        this.user = user;
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOperation that = (UserOperation) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return operations != null ? operations.equals(that.operations) : that.operations == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (operations != null ? operations.hashCode() : 0);
        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

}
