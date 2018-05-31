package com.kasia.core.model;

import com.kasia.core.repository.converter.InstantAttributeConverter;
import com.kasia.core.repository.converter.LocaleAttributeConverter;
import com.kasia.core.repository.converter.ZoneAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable, Model {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "USERNAME", nullable = false, unique = true, length = 32)
    private String username;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "PASSWORD", nullable = false, length = 32)
    private String password;

    @Size(min = 1, max = 32)
    @Column(name = "EMAIL", nullable = false, unique = true, length = 32)
    private String email;

    @NotNull
    @Column(name = "CREATE_ON", nullable = false, length = 32)
    @Convert(converter = InstantAttributeConverter.class)
    private Instant createOn;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ROLES", nullable = false, length = 32)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    @NotNull
    @Convert(converter = LocaleAttributeConverter.class)
    private Locale locale;

    @NotNull
    @Convert(converter = ZoneAttributeConverter.class)
    private ZoneId zoneId;

    @Override
    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCreateOn(Instant createOn) {
        this.createOn = createOn;
    }

    public Instant getCreateOn() {
        return createOn;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
        if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
        if (locale != null ? !locale.equals(user.locale) : user.locale != null) return false;
        return zoneId != null ? zoneId.equals(user.zoneId) : user.zoneId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createOn=" + createOn +
                ", roles=" + roles +
                ", locale=" + locale +
                ", zoneId=" + zoneId +
                '}';
    }
}
