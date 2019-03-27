package com.kasia.model;

import com.kasia.model.repository.converter.BigDecimalAttributeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Operation implements Model, Comparable<Operation> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Element element;
    @OneToOne(fetch = FetchType.EAGER)
    private Place place;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @Convert(converter = BigDecimalAttributeConverter.class)
    private BigDecimal price;
    private LocalDateTime createOn;
    private String description;
    @Enumerated(EnumType.STRING)
    private OperationType type;

    public Operation(User user, Element element, Place place, BigDecimal price, LocalDateTime createOn, String description, OperationType type) {
        this.element = element;
        this.place = place;
        this.user = user;
        this.price = price;
        this.createOn = createOn;
        this.description = description;
        this.type = type;
    }

    public Operation() {
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", element=" + element +
                ", place=" + place +
                ", user=" + user +
                ", price=" + price +
                ", createOn=" + createOn +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (id != operation.id) return false;
        if (element != null ? !element.equals(operation.element) : operation.element != null) return false;
        if (place != null ? !place.equals(operation.place) : operation.place != null) return false;
        if (user != null ? !user.equals(operation.user) : operation.user != null) return false;
        if (price != null ? !price.equals(operation.price) : operation.price != null) return false;
        if (createOn != null ? !createOn.equals(operation.createOn) : operation.createOn != null) return false;
        if (description != null ? !description.equals(operation.description) : operation.description != null)
            return false;
        return type == operation.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (element != null ? element.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Operation o) {
        if (o.getCreateOn().compareTo(this.createOn) >= 0) return 1;
        else return -1;
    }
}
