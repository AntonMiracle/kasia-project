package com.kasia.model;

import com.kasia.model.repository.converter.LocaleAttributeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

@Entity
@Table(name = "project_user")
public class User implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String email;
    private String password;
    private ZoneId zoneId;
    private LocalDateTime createOn;
    private boolean activated;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Convert(converter = LocaleAttributeConverter.class)
    private Locale locale;

    public User(String email, String password, Role role, LocalDateTime createOn, boolean activated, ZoneId zoneId, Locale locale) {
        this.email = email;
        this.password = password;
        this.zoneId = zoneId;
        this.createOn = createOn;
        this.activated = activated;
        this.role = role;
        this.locale = locale;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (activated != user.activated) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (zoneId != null ? !zoneId.equals(user.zoneId) : user.zoneId != null) return false;
        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
        if (role != user.role) return false;
        return locale != null ? locale.equals(user.locale) : user.locale == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (activated ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", zoneId=" + zoneId +
                ", createOn=" + createOn +
                ", activated=" + activated +
                ", role=" + role +
                ", locale=" + locale +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
