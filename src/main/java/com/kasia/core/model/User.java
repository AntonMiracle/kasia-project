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

    public boolean addRole(Role uRole) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles.add(uRole);
    }

    public boolean isHas(Role uRole) {
        return roles != null ? roles.contains(uRole) : false;
    }

    public boolean removeRole(Role uRole) {
        return roles != null ? roles.remove(uRole) : false;
    }

    public void setSecurity(Security uLap) {
        this.security = uLap;
    }

    public Security getSecurity() {
        return this.security;
    }

    public void setDetails(Details uInfo) {
        this.details = uInfo;
    }

    public Details getDetails() {
        return this.details;
    }
}
