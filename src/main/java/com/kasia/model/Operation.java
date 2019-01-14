package com.kasia.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Operation implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Element element;
    @OneToOne(fetch = FetchType.EAGER)
    private ElementProvider elementProvider;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @Embedded
    private Price price;
    private LocalDateTime createOn;

    public Operation(User user, Element element, ElementProvider elementProvider, Price price, LocalDateTime createOn) {
        this.element = element;
        this.elementProvider = elementProvider;
        this.user = user;
        this.price = price;
        this.createOn = createOn;
    }

    public Operation() {

    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", element=" + element +
                ", elementProvider=" + elementProvider +
                ", user=" + user +
                ", price=" + price +
                ", createOn=" + createOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (id != operation.id) return false;
        if (element != null ? !element.equals(operation.element) : operation.element != null) return false;
        if (elementProvider != null ? !elementProvider.equals(operation.elementProvider) : operation.elementProvider != null)
            return false;
        if (user != null ? !user.equals(operation.user) : operation.user != null) return false;
        if (price != null ? !price.equals(operation.price) : operation.price != null) return false;
        return createOn != null ? createOn.equals(operation.createOn) : operation.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (element != null ? element.hashCode() : 0);
        result = 31 * result + (elementProvider != null ? elementProvider.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        return result;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public ElementProvider getElementProvider() {
        return elementProvider;
    }

    public void setElementProvider(ElementProvider elementProvider) {
        this.elementProvider = elementProvider;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User creater) {
        this.user = creater;
    }
}
