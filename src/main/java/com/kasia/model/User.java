package com.kasia.model;

import java.time.LocalDateTime;
import java.util.Set;

public class User implements Model {
    private long id;
    private String email;
    private String password;
    private String nick;
    private Set<Economy> economies;
    private LocalDateTime createOn;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Set<Economy> getEconomies() {
        return economies;
    }

    public void setEconomies(Set<Economy> economies) {
        this.economies = economies;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (nick != null ? !nick.equals(user.nick) : user.nick != null) return false;
        if (economies != null ? !economies.equals(user.economies) : user.economies != null) return false;
        return createOn != null ? createOn.equals(user.createOn) : user.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        result = 31 * result + (economies != null ? economies.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }
}
