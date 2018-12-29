package com.kasia.model;

import java.time.LocalDateTime;

public class Operation implements Model {
    private Element element;
    private ElementProvider elementProvider;
    private Price price;
    private LocalDateTime createOn;

    public Operation(Element element, ElementProvider elementProvider, Price price, LocalDateTime createOn) {
        this.element = element;
        this.elementProvider = elementProvider;
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

        if (element != null ? !element.equals(operation.element) : operation.element != null) return false;
        if (elementProvider != null ? !elementProvider.equals(operation.elementProvider) : operation.elementProvider != null)
            return false;
        if (price != null ? !price.equals(operation.price) : operation.price != null) return false;
        return createOn != null ? createOn.equals(operation.createOn) : operation.createOn == null;
    }

    @Override
    public int hashCode() {
        int result = element != null ? element.hashCode() : 0;
        result = 31 * result + (elementProvider != null ? elementProvider.hashCode() : 0);
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
}
