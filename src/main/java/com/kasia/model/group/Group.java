package com.kasia.model.group;

import com.kasia.model.Model;
import com.kasia.model.user.User;

import java.io.Serializable;

public class Group extends Model implements Serializable {
    private User maker;
    private String name;
    private Type type;

    public Group(String name, User maker, Type type) {
        this.name = name;
        this.maker = maker;
        this.type = type;
    }

    public Group() {

    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public User getMaker() {
        return maker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        if (maker != null ? !maker.equals(group.maker) : group.maker != null) return false;
        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        return type == group.type;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (maker != null ? maker.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "maker=" + maker +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
