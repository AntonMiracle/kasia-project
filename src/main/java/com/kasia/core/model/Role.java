package com.kasia.core.model;

import java.io.Serializable;

public class Role implements Serializable {
    private String name;
    private Long id;

    public Role() {

    }

    public Role(Role role) {
        setId(role.getId());
        setName(role.getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (name != null ? !name.equals(role.name) : role.name != null) return false;
        return id != null ? id.equals(role.id) : role.id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    /**


     public void setName(String name) {
     throwIAE(name == null, "Name is NULL");
     name = name.toUpperCase().trim();
     throwIAE(!NAME.matches(name), "PATTERN: " + NAME.toString()
     + " LENGTH: [" + NAME.MIN_LENGTH + "," + NAME.MAX_LENGTH + "]"
     + "\nCURRENT " + name);
     this.name = name;
     }



     public enum Patterns {
     NAME("^([A-Z0-9]+\\-[A-Z0-9]+)|([A-Z0-9]+)$", 4, 16);
     private Pattern pattern;
     private final int MIN_LENGTH;
     private final int MAX_LENGTH;




     */
}
