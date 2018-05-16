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
    private long id;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "NAME", nullable = false, unique = true, length = 32)
    private String name;
    private boolean isNull;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isNull() {
        return isNull;
    }

    @Override
    public void setNull(boolean isNull) {
        this.isNull = isNull;
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
        if (isNull != groupType.isNull) return false;
        return name != null ? name.equals(groupType.name) : groupType.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isNull ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isNull=" + isNull +
                '}';
    }
}
