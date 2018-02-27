package com.kasia.core.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private Details details;
    private LocalDateTime createOn;
    private Set<Role> roles;
    private Security security;

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public LocalDateTime getCreateOn() {
        return this.createOn;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean addRole(Role role) {
        initRoles();
        return roles.add(new Role(role));
    }

    public boolean isHas(Role role) {
        return roles != null ? roles.contains(role) : false;
    }

    public boolean removeRole(Role role) {
        return roles != null ? roles.remove(role) : false;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public Security getSecurity() {
        return this.security;
    }

    public void setDetails(Details details) {
        this.details = new Details(details);
    }

    public Details getDetails() {
        return this.details;
    }

    public Set<Role> getRoles() {
        initRoles();
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        initRoles();
        for (Role role : roles) this.roles.add(role);
    }

    private void initRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
    }
}
