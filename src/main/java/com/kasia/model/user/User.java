package com.kasia.model.user;

import com.kasia.model.Model;

import java.util.Set;

public class User implements Model {
    private String username;
    private String password;
    private Set<String> groups;
    private String email;
    private long id;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (groups != null ? !groups.equals(user.groups) : user.groups != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
