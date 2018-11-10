package com.kasia.model;

import com.kasia.repository.converter.LocalDateTimeAttributeConverter;
import com.kasia.repository.converter.ZoneIdAttributeConverter;
import com.kasia.validation.user.UserConstraint;

import javax.inject.Named;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Named
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
    @Column(name = "NICK", nullable = false, unique = true)
    private String nick;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES")
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_BUDGETS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "BUDGET_ID"))
    private Set<Budget> budgets;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_ARTICLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
    private Set<Article> articles;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_EMPLOYERS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYER_ID"))
    private Set<Employer> employers;
    @Column(name = "CREATE_ON", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createOn;
    @Column(name = "ZONE_ID", nullable = false)
    @Convert(converter = ZoneIdAttributeConverter.class)
    private ZoneId zoneId;

    public User(Set<Role> role, String email, String nick, String password, ZoneId zoneId, LocalDateTime createOn) {
        this.roles = role;
        this.email = email;
        this.nick = nick;
        this.password = password;
        this.zoneId = zoneId;
        this.createOn = createOn;
    }

    public User() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(Set<Budget> budgets) {
        this.budgets = budgets;
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

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Employer> getEmployers() {
        return employers;
    }

    public void setEmployers(Set<Employer> employers) {
        this.employers = employers;
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
        if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
        if (budgets != null ? !budgets.equals(user.budgets) : user.budgets != null) return false;
        if (articles != null ? !articles.equals(user.articles) : user.articles != null) return false;
        if (employers != null ? !employers.equals(user.employers) : user.employers != null) return false;
        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
        return zoneId != null ? zoneId.equals(user.zoneId) : user.zoneId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (budgets != null ? budgets.hashCode() : 0);
        result = 31 * result + (articles != null ? articles.hashCode() : 0);
        result = 31 * result + (employers != null ? employers.hashCode() : 0);
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
                ", roles=" + roles +
                ", budgets=" + budgets +
                ", articles=" + articles +
                ", employers=" + employers +
                ", createOn=" + createOn +
                ", zoneId=" + zoneId +
                '}';
    }

    public enum Role {
        ADMINISTRATOR, USER
    }
}

