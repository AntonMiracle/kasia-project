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

    public User() {
    }

    public User(Long id, Details details, LocalDateTime createOn, Set<Role> roles, Security security) {
        this.id = id;
//        this.details = new Details(details);
        this.createOn = createOn;
        for (Role role : roles) addRole(role);
        this.security = new Security(security);
    }

    public User(User user) {
        this(user.getId(), user.getDetails(), user.getCreateOn(), user.getRoles(), user.getSecurity());
    }

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

    public void setSecurity(Security security) {
        this.security = security;
    }

    public Security getSecurity() {
        return this.security;
    }

    public void setDetails(Details details) {
//        this.details = new Details(details);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (details != null ? !details.equals(user.details) : user.details != null) return false;
        if (createOn != null ? !createOn.equals(user.createOn) : user.createOn != null) return false;
        if(roles !=null && user.getRoles() != null){
            for(Role role : user.getRoles()) if(!isHas(role)) return false;
        }else{
            return false;
        }
        return security != null ? security.equals(user.security) : user.security == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (security != null ? security.hashCode() : 0);
        return result;
    }

    private void initRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
    }
}
