package com.kasia.core.model;

public class Urole {
    private Long id;
    private String name;

    public Urole() {

    }

    public Urole(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Urole urole = (Urole) o;

        if (this.name != null ? !this.name.equals(urole.name) : urole.name != null) return false;
        return id != null ? id.equals(urole.id) : urole.id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
