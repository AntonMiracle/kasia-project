package com.kasia.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public class User implements Model{
    private String email;// unique, login
    private String name;// unique, pattern
    private String password;
    private ZoneId zoneId;
    private Locale locale;
    private LocalDateTime createOn;

    public User() {
    }

    public User(String email, String name, String password, ZoneId zoneId, Locale locale, LocalDateTime createOn) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.zoneId = zoneId;
        this.locale = locale;
        this.createOn = createOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (zoneId != null ? !zoneId.equals(user.zoneId) : user.zoneId != null) return false;
        if (locale != null ? !locale.equals(user.locale) : user.locale != null) return false;
        return createOn != null ? createOn.equals(user.createOn) : user.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }
}
