package com.kasia.core.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private Uinfo uInfo;
    private LocalDateTime createOn;
    private Set<Urole> uRoles;
    private Ulap uLap;

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

    public boolean addRole(Urole uRole) {
        if (uRoles == null) {
            uRoles = new HashSet<>();
        }
        return uRoles.add(uRole);
    }

    public boolean isHas(Urole uRole) {
        return uRoles != null ? uRoles.contains(uRole) : false;
    }

    public boolean removeRole(Urole uRole) {
        return uRoles != null ? uRoles.remove(uRole) : false;
    }

    public void setUlap(Ulap uLap) {
        this.uLap = uLap;
    }

    public Ulap getUlap() {
        return this.uLap;
    }

    public void setInfo(Uinfo uInfo) {
        this.uInfo = uInfo;
    }

    public Uinfo getInfo() {
        return this.uInfo;
    }
}
