package com.kasia.model.user;

import com.kasia.model.Model;
import com.kasia.model.group.Group;
import com.kasia.validation.user.EmailConstraint;
import com.kasia.validation.user.PasswordConstraint;
import com.kasia.validation.user.UsernameConstraint;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

public class User extends Model implements Serializable {
    @NotNull
    private Instant create;
    @NotNull
    private Locale locale;
    @UsernameConstraint
    private String username;
    @PasswordConstraint
    private String password;
    @NotNull
    private Set<Group> groups;
    @EmailConstraint
    private String email;
    @NotNull
    private ZoneId zoneId;

    public User() {

    }

    public User(String username, String password, String email, Set<Group> groups, Instant create, Locale locale, ZoneId zoneId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.groups = groups;
        this.create = create;
        this.locale = locale;
        this.zoneId = zoneId;
    }

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

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCreate(Instant create) {
        this.create = create;
    }

    public Instant getCreate() {
        return create;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    @Override
    public String toString() {
        return "User{" +
                "create=" + create +
                ", locale=" + locale +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", groups=" + groups +
                ", email='" + email + '\'' +
                ", zoneId=" + zoneId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (create != null ? !create.equals(user.create) : user.create != null) return false;
        if (locale != null ? !locale.equals(user.locale) : user.locale != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (groups != null ? !groups.equals(user.groups) : user.groups != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return zoneId != null ? zoneId.equals(user.zoneId) : user.zoneId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (create != null ? create.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        return result;
    }
}
