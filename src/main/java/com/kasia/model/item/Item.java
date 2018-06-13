package com.kasia.model.item;

import com.kasia.model.Model;
import com.kasia.model.group.Group;
import com.kasia.model.price.Price;
import com.kasia.model.unit.Unit;
import com.kasia.model.user.User;

import java.io.Serializable;

public class Item extends Model implements Serializable {
    private Price price;
    private Unit unit;
    private Group group;
    private String name;
    private String description;
    private User maker;

    public Item(String name, Unit unit, Price price, String description, Group group, User maker) {
        this.name = name;
        this.description = description;
        this.maker = maker;
        this.group = group;
        this.unit = unit;
        this.price = price;
    }

    public Item() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public User getMaker() {
        return maker;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "Item{" +
                "price=" + price +
                ", unit=" + unit +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", maker=" + maker +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Item item = (Item) o;

        if (price != null ? !price.equals(item.price) : item.price != null) return false;
        if (unit != null ? !unit.equals(item.unit) : item.unit != null) return false;
        if (group != null ? !group.equals(item.group) : item.group != null) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        return maker != null ? maker.equals(item.maker) : item.maker == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (maker != null ? maker.hashCode() : 0);
        return result;
    }
}
