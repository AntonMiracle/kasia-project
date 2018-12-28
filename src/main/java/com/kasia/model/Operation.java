package com.kasia.model;

import java.time.LocalDateTime;

public class Operation implements Model{
    private long id;
    private Element element;
    private Price price;
    private LocalDateTime createOn;

    public Operation(long id, Element element, Price price, LocalDateTime createOn) {
        this.id = id;
        this.element = element;
        this.price = price;
        this.createOn = createOn;
    }

    public Operation() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (id != operation.id) return false;
        if (element != null ? !element.equals(operation.element) : operation.element != null) return false;
        if (price != null ? !price.equals(operation.price) : operation.price != null) return false;
        return createOn != null ? createOn.equals(operation.createOn) : operation.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (element != null ? element.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createOn != null ? createOn.hashCode() : 0);
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
}
