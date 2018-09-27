package com.kasia.model;

import com.kasia.repository.converter.LocalDateTimeAttributeConverter;
import com.kasia.repository.converter.ZoneIdAttributeConvarter;
import com.kasia.validation.user.UserConstraint;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@UserConstraint
@Entity
@Table(name = "USERS")
public class User implements Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "NICK", nullable = false)
    private String nick;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_ECONOMIES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ECONOMY_ID"))
    private Set<Economy> economies;
    @Column(name = "CREATEON", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createOn;
    @Column(name = "ZONEID", nullable = false)
    @Convert(converter = ZoneIdAttributeConvarter.class)
    private ZoneId zoneId;

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

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
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
        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
        return zoneId != null ? zoneId.equals(user.zoneId) : user.zoneId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        result = 31 * result + (economies != null ? economies.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                ", economies=" + economies +
                ", createOn=" + createOn +
                ", zoneId=" + zoneId +
                '}';
    }
}
