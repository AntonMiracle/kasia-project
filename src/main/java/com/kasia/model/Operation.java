package com.kasia.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Operation implements Model, Comparable<Operation> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Element element;
    @OneToOne(fetch = FetchType.EAGER)
    private Provider provider;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @Embedded
    private Price price;
    private LocalDateTime createOn;
    private String description;

    public Operation(User user, Element element, Provider provider, Price price, LocalDateTime createOn, String description) {
        this.element = element;
        this.provider = provider;
        this.user = user;
        this.price = price;
        this.createOn = createOn;
        this.description = description;
    }

    public Operation() {

    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", element=" + element +
                ", provider=" + provider +
                ", user=" + user +
                ", price=" + price +
                ", createOn=" + createOn +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (id != operation.id) return false;
        if (element != null ? !element.equals(operation.element) : operation.element != null) return false;
        if (provider != null ? !provider.equals(operation.provider) : operation.provider != null) return false;
        if (user != null ? !user.equals(operation.user) : operation.user != null) return false;
        if (price != null ? !price.equals(operation.price) : operation.price != null) return false;
        if (createOn != null ? !createOn.equals(operation.createOn) : operation.createOn != null) return false;
        return description != null ? description.equals(operation.description) : operation.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (element != null ? element.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Operation o) {
        if (o.getCreateOn().compareTo(this.createOn) >= 0) return 1;
        else return -1;
    }
}
