package com.kasia.core.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "GROUP_TYPES")
public class GroupType implements Serializable, Model {
    @Id
    @Column(name = "GROUP_TYPES_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "NAME", nullable = false, unique = true, length = 32)
    private String name;

    @Override
    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupType groupType = (GroupType) o;

        if (id != groupType.id) return false;
        return name != null ? name.equals(groupType.name) : groupType.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
