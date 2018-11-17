package com.kasia.model;

import com.kasia.validation.ValidationService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "EMPLOYERS")
public class Employer implements Model {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Pattern(regexp = ValidationService.NAME)
    @Column(name = "AMOUNT", nullable = false)
    private String name;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    public Employer() {
    }

    public Employer(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employer employer = (Employer) o;

        if (id != employer.id) return false;
        if (name != null ? !name.equals(employer.name) : employer.name != null) return false;
        return description != null ? description.equals(employer.description) : employer.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public long getId() {

        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
