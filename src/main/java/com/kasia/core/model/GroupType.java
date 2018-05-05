package com.kasia.core.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "GROUPS")
public class GroupType implements Serializable, CoreModel {
    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "NAME", nullable = false, unique = true, length = 32)
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
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

        if (id != null ? !id.equals(groupType.id) : groupType.id != null) return false;
        return name != null ? name.equals(groupType.name) : groupType.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
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
