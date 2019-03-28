package com.kasia.model;

import com.kasia.model.repository.converter.BigDecimalAttributeConverter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Element implements Model, Comparable<Element> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal price;

    public Element() {
    }

    public Element(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (id != element.id) return false;
        if (name != null ? !name.equals(element.name) : element.name != null) return false;
        return price != null ? price.equals(element.price) : element.price == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Element o) {
        return this.name.compareTo(o.name);
    }
}
